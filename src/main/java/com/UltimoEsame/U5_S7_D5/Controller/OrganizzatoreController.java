package com.UltimoEsame.U5_S7_D5.Controller;

import com.UltimoEsame.U5_S7_D5.Model.Evento;
import com.UltimoEsame.U5_S7_D5.Payload.EventoDTO;
import com.UltimoEsame.U5_S7_D5.Service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizzatore")
public class OrganizzatoreController {

    @Autowired
    private EventoService eventoService;

    // POSTMAN --> Es: http://localhost:8080/organizzatore/eventi
    @PostMapping("/eventi")
    public ResponseEntity<?> creaEvento(@Validated @RequestBody EventoDTO eventoDTO) {
        System.out.println("Ricevuto JSON: " + eventoDTO);
        System.out.println("nPosti ricevuto: " + eventoDTO.getNPosti());
        try {
            Evento eventoCreato = eventoService.creaEvento(eventoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventoCreato);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // POSTMAN --> Es: http://localhost:8080/organizzatore/eventi
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

    // POSTMAN --> Es: http://localhost:8080/organizzatore/eventi/2
    @GetMapping("/eventi/{idOrganizzatore}")
    //Per questo serve sempre l'authentication
    public ResponseEntity<String> listaEventiPersonali(@PathVariable Long idOrganizzatore) {
        try {
            String lista = eventoService.visualizzaEventiPersonali(idOrganizzatore);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/eventi/{idEvento}")
    public ResponseEntity<EventoDTO> patchEvento(@PathVariable Long idEvento, @RequestBody EventoDTO eventoDTO) {
        try {
            Evento eventoAggiornato = eventoService.patchEvento(idEvento, eventoDTO);
            EventoDTO eventoDTOResponse = eventoService.entity_dto(eventoAggiornato); // Uso metodo per convertire l'evento in DTO
            return ResponseEntity.ok(eventoDTOResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // POSTMAN --> Es: http://localhost:8080/organizzatore/eventi/2/1
    @DeleteMapping("/eventi/{idOrganizzatore}/{idEvento}")
    public ResponseEntity<String> cancellaEvento(@PathVariable Long idOrganizzatore, @PathVariable Long idEvento){
        try {
            String lista = eventoService.cancellaEvento(idEvento, idOrganizzatore);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
