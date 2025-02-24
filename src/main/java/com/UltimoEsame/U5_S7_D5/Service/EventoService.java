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

    public String creaEvento(EventoDTO eventoDTO) {
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

        EventoDTO dto = entity_dto(evento);
        eventoRepository.save(evento);
        return dto.toString();
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

    public Evento patchEvento(Long idEvento, EventoDTO eventoDTO) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento non trovato con ID " + idEvento));

        // Verifica e aggiorna solo i campi che sono stati forniti nel DTO
        if (eventoDTO.getTitolo() != null) {
            evento.setTitolo(eventoDTO.getTitolo());
        }
        if (eventoDTO.getDescrizione() != null) {
            evento.setDescrizione(eventoDTO.getDescrizione());
        }
        if (eventoDTO.getData() != null) {
            evento.setData(eventoDTO.getData());
        }
        if (eventoDTO.getLuogo() != null) {
            evento.setLuogo(eventoDTO.getLuogo());
        }
        if (eventoDTO.getNPosti() != null) {
            evento.setNPosti(eventoDTO.getNPosti());
        }
        if (eventoDTO.getCostoBiglietto() >= 0) {
            evento.setCostoBiglietto(eventoDTO.getCostoBiglietto());
        }

        // L'organizzatore non deve essere aggiornato, quindi non includiamo alcuna modifica per il campo 'organizzatore'

        return eventoRepository.save(evento);
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
