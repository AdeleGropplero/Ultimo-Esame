/*
package com.UltimoEsame.U5_S7_D5.Controller;

import com.UltimoEsame.U5_S7_D5.Model.Evento;
import com.UltimoEsame.U5_S7_D5.Payload.EventoDTO;
import com.UltimoEsame.U5_S7_D5.Service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizzatore")
public class OrganizzatoreController {

    @Autowired
    private EventoService eventoService;

    // POSTMAN --> Es: http://localhost:8080/organizzatore/eventi
    @PostMapping("/eventi")
    public ResponseEntity<?> creaEvento(@Validated @RequestBody EventoDTO eventoDTO) {
        try {
            Evento eventoCreato = eventoService.creaEvento(eventoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventoCreato);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
*/
