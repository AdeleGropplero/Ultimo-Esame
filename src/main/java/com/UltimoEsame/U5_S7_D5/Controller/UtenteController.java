package com.UltimoEsame.U5_S7_D5.Controller;

import com.UltimoEsame.U5_S7_D5.Exception.EmailDuplicateException;
import com.UltimoEsame.U5_S7_D5.Exception.UsernameDuplicateException;
import com.UltimoEsame.U5_S7_D5.Payload.request.LoginRequest;
import com.UltimoEsame.U5_S7_D5.Payload.request.RegistrationRequest;
import com.UltimoEsame.U5_S7_D5.Payload.response.JwtResponse;
import com.UltimoEsame.U5_S7_D5.Security.JWT.JwtUtils;
import com.UltimoEsame.U5_S7_D5.Security.Services.UserDetailsImpl;
import com.UltimoEsame.U5_S7_D5.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    AuthenticationManager managerAuth;

    @Autowired
    JwtUtils jwtUtils;

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

        // VALIDAZIONE
        if (validation.hasErrors()) {
            StringBuilder errori = new StringBuilder("Problemi nella validazione dati :\n");

            for (ObjectError errore : validation.getAllErrors()) {
                errori.append(errore.getDefaultMessage()).append("\n");
            }

            return new ResponseEntity<>(errori.toString(), HttpStatus.BAD_REQUEST);

        }
        //Qui ci generiamo un oggetto che occorre per l'autenticazione
        UsernamePasswordAuthenticationToken tokenPerAuth =
                new UsernamePasswordAuthenticationToken(loginUtente.getUsername(), loginUtente.getPassword());

        // Utilizziamo il gestore delle autenticazioni che si basa su Useername e Password
        // Recuperiamo l'autenticazione attraverso il metodo authenticate
        Authentication authentication = managerAuth.authenticate(tokenPerAuth);

        // Impostare l'autenticazione nel contesto di sicurezza Spring
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generiamo il TOKEN FINALE (String)
        String tokenFinale = jwtUtils.createJwtToken(authentication);

        // Recuperiamo qui le informazioni che vogliamo inserire nella risposta al client
        UserDetailsImpl dettagliUtente= (UserDetailsImpl) authentication.getDetails();
        List<String> ruoliWeb = dettagliUtente.getAuthorities().stream().map(
                GrantedAuthority::getAuthority).toList(); // che è come scrivere: item -> item.getAuthority()).toList()

        // Creiamo un oggetto JWT response
        JwtResponse responseJwt = new JwtResponse(dettagliUtente.getUsername(), dettagliUtente.getId(), dettagliUtente.getEmail(), ruoliWeb, tokenFinale);

        //Gestione della risposta al client
        return new ResponseEntity<>(responseJwt, HttpStatus.OK);
    }
}


