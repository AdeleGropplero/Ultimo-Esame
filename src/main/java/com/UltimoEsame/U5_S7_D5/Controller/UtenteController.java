package com.UltimoEsame.U5_S7_D5.Controller;

import com.UltimoEsame.U5_S7_D5.Model.Evento;
import com.UltimoEsame.U5_S7_D5.Ruolo.RuoloService;
import com.UltimoEsame.U5_S7_D5.Service.EventoService;
import com.UltimoEsame.U5_S7_D5.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    UtenteService utenteService;

    @Autowired
    EventoService eventoService;

    // POSTMAN --> Es: http://localhost:8080/utente/eventi
    @GetMapping("/eventi")
    //Per questo serve sempre l'authentication
    public ResponseEntity<String> listaEventi() {
        try {
            String lista = eventoService.visualizzaEventi();
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}

