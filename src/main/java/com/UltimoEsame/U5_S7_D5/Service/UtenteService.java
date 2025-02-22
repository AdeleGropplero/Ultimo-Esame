package com.UltimoEsame.U5_S7_D5.Service;

import com.UltimoEsame.U5_S7_D5.Exception.EmailDuplicateException;
import com.UltimoEsame.U5_S7_D5.Exception.UsernameDuplicateException;
import com.UltimoEsame.U5_S7_D5.Model.Utente;
import com.UltimoEsame.U5_S7_D5.Payload.response.UtenteDTO;
import com.UltimoEsame.U5_S7_D5.Repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UtenteService {
    @Autowired
    UtenteRepository utenteRepository;


    public String insertUtente(UtenteDTO utenteDTO) {
        checkDuplicatedKey(utenteDTO.getUsername(), utenteDTO.getEmail());
        Utente utente = dto_entity(utenteDTO);
        utenteRepository.save(utente);
        return "Utente " + utente.getUsername() + ", inserito correttamente in DB con id: " + utente.getId();
    }

    public void checkDuplicatedKey(String username, String email) {
        if (utenteRepository.existsByEmail(email)) {
            throw new EmailDuplicateException("Email già utilizzata!");
        }
        if (utenteRepository.existsByUsername(username)) {
            throw new UsernameDuplicateException("Username già in uso!");
        }
    }

    //CRUD UTENTE GENERICO----------------------------------------------------------------------------------------
    // L'utente semplice può solo:
    // VISUALIZZARE una lista eventi. (Get)
    // PRENOTARE posti agli eventi disponibili (check sulla disponibilità). (Post)
    // (EXTRA) VISUALIZZARE eventi prenotati. (Get)
    // (EXTRA) ANNULLARE la prenotazione. (Delete)

    //CRUD UTENTE ORGANIZZATORE----------------------------------------------------------------------------------------
    // L'organizzatore può:
    // VISUALIZZARE una lista eventi. (Get)
    // VISUALIZZARE la lista dei suoi eventi con i posti rimasti. (Get)
    // CREARE eventi. (Post)
    // MODIFICARE i propri eventi. (Patch)
    // CANCELLARE i propri eventi. (Delete)

    //CRUD UTENTE ADMIN----------------------------------------------------------------------------------------
    // L'admin può:
    // MODIFICARE le autorizzazioni degli altri utenti.


    //Travasi ----------------------------------------------------------------------------------------
    public Utente dto_entity(UtenteDTO utenteDTO) {
        Utente utente = new Utente();
        utente.setUsername(utenteDTO.getUsername());
        utente.setEmail(utenteDTO.getEmail());
        utente.setPassword(utenteDTO.getPassword());
        return utente;
    }

    public UtenteDTO entity_dto(Utente utente) {
        UtenteDTO utenteDTO = new UtenteDTO();
        utenteDTO.setUsername(utente.getUsername());
        utenteDTO.setEmail(utente.getEmail());
        return utenteDTO;
    }


}
