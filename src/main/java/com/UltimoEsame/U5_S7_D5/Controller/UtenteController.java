package com.UltimoEsame.U5_S7_D5.Controller;

import com.UltimoEsame.U5_S7_D5.Exception.EmailDuplicateException;
import com.UltimoEsame.U5_S7_D5.Exception.UsernameDuplicateException;
import com.UltimoEsame.U5_S7_D5.Model.Utente;
import com.UltimoEsame.U5_S7_D5.Payload.request.LoginRequest;
import com.UltimoEsame.U5_S7_D5.Payload.request.RegistrationRequest;
import com.UltimoEsame.U5_S7_D5.Payload.response.JwtResponse;
import com.UltimoEsame.U5_S7_D5.Repository.UtenteRepository;
import com.UltimoEsame.U5_S7_D5.Security.JWT.JwtUtils;
import com.UltimoEsame.U5_S7_D5.Security.Services.UserDetailsImpl;
import com.UltimoEsame.U5_S7_D5.Security.Services.UserDetailsServiceImpl;
import com.UltimoEsame.U5_S7_D5.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    UtenteService utenteService;


    private AuthenticationManager managerAuth;

    @Autowired
    JwtUtils jwtUtils;


    @Autowired
    public UtenteController( AuthenticationManager managerAuth) {

        this.managerAuth = managerAuth;
    }

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/new")
    public ResponseEntity<String> insertUtente(@Validated @RequestBody RegistrationRequest nuovoUtente, BindingResult validation) {
        try {
            if (validation.hasErrors()) {
                StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");

                for (ObjectError errore : validation.getAllErrors()) {
                    errori.append(errore.getDefaultMessage()).append("\n");
                }

                return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
            }
            String messaggio = utenteService.insertUtente(nuovoUtente);
            return new ResponseEntity<>(messaggio, HttpStatus.OK);
        } catch (UsernameDuplicateException e) {
            return new ResponseEntity<>("Username già utilizzato", HttpStatus.BAD_REQUEST);
        } catch (EmailDuplicateException e) {
            return new ResponseEntity<>("Email già presente in database", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginUtente, BindingResult validation) {
        System.out.println("Username ricevuto: " + loginUtente.getUsername());
        System.out.println("Password ricevuta: " + loginUtente.getPassword());

        // VALIDAZIONE INPUT
        if (validation.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati:\n");
            for (ObjectError errore : validation.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginUtente.getUsername());
        // AUTENTICAZIONE CON SPRING SECURITY
        try {

            UsernamePasswordAuthenticationToken tokenPerAuth = new UsernamePasswordAuthenticationToken(loginUtente.getUsername(), loginUtente.getPassword());
            System.out.println("before authentication: " + loginUtente.getUsername());

            Authentication authentication = managerAuth.authenticate(tokenPerAuth);
            System.out.println("Authentication obj: " + authentication);
            System.out.println("is autenticated? " + authentication.isAuthenticated());

            // IMPOSTA L'AUTENTICAZIONE NEL CONTESTO DI SICUREZZA
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // CREA IL TOKEN JWT
            String tokenFinale = jwtUtils.createJwtToken(authentication);

            // OTTIENI DETTAGLI DELL'UTENTE AUTENTICATO
            UserDetailsImpl dettagliUtente = (UserDetailsImpl) authentication.getPrincipal();
            List<String> ruoliWeb = dettagliUtente.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            System.out.println("Token JWT generato: " + tokenFinale);

            // CREIAMO L'OGGETTO DI RISPOSTA JWT
            JwtResponse responseJwt = new JwtResponse(
                    dettagliUtente.getUsername(),
                    dettagliUtente.getId(),
                    dettagliUtente.getEmail(),
                    ruoliWeb,
                    tokenFinale
            );

            // RESTITUISCE LA RISPOSTA CON IL TOKEN
            return new ResponseEntity<>(responseJwt, HttpStatus.OK);
        } catch (AuthenticationException e){
            return new ResponseEntity<>("Credenziali non Valide", HttpStatus.UNAUTHORIZED);
        }

    }

}


