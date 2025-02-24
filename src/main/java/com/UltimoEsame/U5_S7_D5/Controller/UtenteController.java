package com.UltimoEsame.U5_S7_D5.Controller;

import com.UltimoEsame.U5_S7_D5.Model.Evento;
import com.UltimoEsame.U5_S7_D5.Payload.PrenotazioneDTO;
import com.UltimoEsame.U5_S7_D5.Ruolo.RuoloService;
import com.UltimoEsame.U5_S7_D5.Service.EventoService;
import com.UltimoEsame.U5_S7_D5.Service.PrenotazioneService;
import com.UltimoEsame.U5_S7_D5.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    UtenteService utenteService;

    @Autowired
    EventoService eventoService;

    @Autowired
    PrenotazioneService prenotazioneService;

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

    @PostMapping("/prenotazione")
    public ResponseEntity<String> inserisciPrenotazione(@Validated @RequestBody PrenotazioneDTO dto) {
        try {
            String prenotazione = prenotazioneService.createPrenotazione(dto);
            return ResponseEntity.ok(prenotazione);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}

