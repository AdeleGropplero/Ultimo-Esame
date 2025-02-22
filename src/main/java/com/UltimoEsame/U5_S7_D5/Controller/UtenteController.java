package com.UltimoEsame.U5_S7_D5.Controller;

import com.UltimoEsame.U5_S7_D5.Exception.EmailDuplicateException;
import com.UltimoEsame.U5_S7_D5.Exception.UsernameDuplicateException;
import com.UltimoEsame.U5_S7_D5.Payload.request.RegistrationRequest;
import com.UltimoEsame.U5_S7_D5.Security.JWT.JwtUtils;
import com.UltimoEsame.U5_S7_D5.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    UtenteService utenteService;

/*    @Autowired
    AuthenticationManager managerAuth;*/

    @Autowired
    JwtUtils utilitiesJwt;

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
}


