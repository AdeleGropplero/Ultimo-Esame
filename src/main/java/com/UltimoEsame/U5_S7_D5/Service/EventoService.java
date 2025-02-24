package com.UltimoEsame.U5_S7_D5.Service;

import com.UltimoEsame.U5_S7_D5.Model.Evento;
import com.UltimoEsame.U5_S7_D5.Model.Utente;
import com.UltimoEsame.U5_S7_D5.Payload.EventoDTO;
import com.UltimoEsame.U5_S7_D5.Repository.EventoRepository;
import com.UltimoEsame.U5_S7_D5.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    //CRUD UTENTE ORGANIZZATORE-----(Vedi OrganizzatoreController)-----------------------------------------------------------------------------------
    // L'organizzatore può:
    // CREARE eventi. (Post) //✅
    // VISUALIZZARE una lista eventi. (Get) //✅
    // VISUALIZZARE la lista dei suoi eventi con i posti rimasti. (Get) //✅
    // MODIFICARE i propri eventi. (Patch)
    // CANCELLARE i propri eventi. (Delete)

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

    public String visualizzaEventi(){
        List<Evento> eventi = eventoRepository.findAll();
        StringBuilder sb = new StringBuilder();
        eventi.forEach(evento -> {
            EventoDTO dto = entity_dto(evento);
            sb.append(dto.toString()).append("\n");
        });
        return sb.toString();
    }

    public String visualizzaEventiPersonali(Long idOrganizzatore){
        List<Evento> eventi = eventoRepository.findAllByOrganizzatoreId(idOrganizzatore)
                .orElseThrow(() -> new RuntimeException("Nessun evento trovato per l'organizzatore con ID " + idOrganizzatore));

        StringBuilder sb = new StringBuilder();
        eventi.forEach(evento -> {
            EventoDTO dto = entity_dto(evento);
            sb.append(dto.toString()).append("\n");
        });
        return sb.toString();
    }

    public String cancellaEvento(Long idEvento, Long idOrganizzatore){
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Nessun evento trovato con ID " + idEvento));
        // Verifica che l'organizzatore che sta cercando di cancellare l'evento sia lo stesso che l'ha creato
        if (!evento.getOrganizzatore().getId().equals(idOrganizzatore)) {
            throw new RuntimeException("Non sei autorizzato a cancellare questo evento");
        }
        eventoRepository.delete(evento);
        EventoDTO eventoDTO = entity_dto(evento);
        return eventoDTO.toString();
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
