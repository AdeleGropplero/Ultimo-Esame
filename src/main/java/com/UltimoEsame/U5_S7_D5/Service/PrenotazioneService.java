package com.UltimoEsame.U5_S7_D5.Service;

import com.UltimoEsame.U5_S7_D5.Model.Evento;
import com.UltimoEsame.U5_S7_D5.Model.Prenotazione;
import com.UltimoEsame.U5_S7_D5.Model.Utente;
import com.UltimoEsame.U5_S7_D5.Payload.EventoDTO;
import com.UltimoEsame.U5_S7_D5.Payload.PrenotazioneDTO;
import com.UltimoEsame.U5_S7_D5.Repository.EventoRepository;
import com.UltimoEsame.U5_S7_D5.Repository.PrenotazioneRepository;
import com.UltimoEsame.U5_S7_D5.Repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PrenotazioneService {
    @Autowired
    PrenotazioneRepository prenotazioneRepository;

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    UtenteRepository utenteRepository;

    public String createPrenotazione(PrenotazioneDTO dto) {
        Utente acquirente = utenteRepository.findById(dto.getUtenteId()).orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Evento evento = eventoRepository.findById(dto.getEventoId()).orElseThrow(() -> new RuntimeException("Evento non trovato"));

        Prenotazione prenotazione = new Prenotazione(acquirente, evento, dto.getNPostiPrenotati());

        PrenotazioneDTO prenotazioneDTO = entity_dto(prenotazione);

        return prenotazioneDTO.toString();
    }

    public PrenotazioneDTO entity_dto(Prenotazione prenotazione) {
        return new PrenotazioneDTO(
                prenotazione.getUtente().getId(),
                prenotazione.getEvento().getId(),
                prenotazione.getNPostiPrenotati(),
                prenotazione.getSpesa()
        );
    }
}
