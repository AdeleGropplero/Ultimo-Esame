package com.UltimoEsame.U5_S7_D5.Service;

import com.UltimoEsame.U5_S7_D5.Model.Evento;
import com.UltimoEsame.U5_S7_D5.Model.Utente;
import com.UltimoEsame.U5_S7_D5.Payload.EventoDTO;
import com.UltimoEsame.U5_S7_D5.Repository.EventoRepository;
import com.UltimoEsame.U5_S7_D5.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public Evento creaEvento(EventoDTO eventoDTO) {
        // Recupera l'organizzatore dall'ID
        Utente organizzatore = utenteRepository.findById(eventoDTO.getOrganizzatoreId())
                .orElseThrow(() -> new RuntimeException("Organizzatore non trovato"));

        // Crea l'entità Evento dal DTO
        Evento evento = Evento.builder()
                .titolo(eventoDTO.getTitolo())
                .descrizione(eventoDTO.getDescrizione())
                .data(eventoDTO.getData())
                .luogo(eventoDTO.getLuogo())
                .nPosti(eventoDTO.getNPosti())
                .costoBiglietto(eventoDTO.getCostoBiglietto())
                .organizzatore(organizzatore)
                .build();

        // Salva l'evento nel database
        return eventoRepository.save(evento);
    }

    public EventoDTO entity_dto(Evento evento) {
        return new EventoDTO(
                evento.getTitolo(),
                evento.getDescrizione(),
                evento.getData(),
                evento.getLuogo(),
                evento.getNPosti(),
                evento.getCostoBiglietto(),
                evento.getOrganizzatore().getId() // Passiamo l'ID dell'organizzatore
        );
    }

}
