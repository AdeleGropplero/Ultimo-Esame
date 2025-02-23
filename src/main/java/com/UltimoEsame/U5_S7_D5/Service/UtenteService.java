package com.UltimoEsame.U5_S7_D5.Service;

import com.UltimoEsame.U5_S7_D5.Ruolo.ERuolo;
import com.UltimoEsame.U5_S7_D5.Exception.EmailDuplicateException;
import com.UltimoEsame.U5_S7_D5.Exception.UsernameDuplicateException;
import com.UltimoEsame.U5_S7_D5.Model.Utente;
import com.UltimoEsame.U5_S7_D5.Payload.request.RegistrationRequest;
import com.UltimoEsame.U5_S7_D5.Payload.UtenteDTO;
import com.UltimoEsame.U5_S7_D5.Repository.UtenteRepository;
import com.UltimoEsame.U5_S7_D5.Ruolo.Ruolo;
import com.UltimoEsame.U5_S7_D5.Ruolo.RuoloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UtenteService {
    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    RuoloRepository ruoloRepository;

    public void checkDuplicatedKey(String username, String email) {
        if (utenteRepository.existsByEmail(email)) {
            throw new EmailDuplicateException("Email già utilizzata!");
        }
        if (utenteRepository.existsByUsername(username)) {
            throw new UsernameDuplicateException("Username già in uso!");
        }
    }

    //CRUD UTENTE ORGANIZZATORE-----(Vedi OrganizzatoreController)-----------------------------------------------------------------------------------
    // L'organizzatore può:
    // VISUALIZZARE una lista eventi. (Get)
    // VISUALIZZARE la lista dei suoi eventi con i posti rimasti. (Get)
    // CREARE eventi. (Post)
    // MODIFICARE i propri eventi. (Patch)
    // CANCELLARE i propri eventi. (Delete)


    //CRUD UTENTE ADMIN-----(Vedi AdminController)-----------------------------------------------------------------------------------
    // L'admin può:
    // MODIFICARE le autorizzazioni degli altri utenti.

    public String modificaRuoloUtente(Long idUtente, Long idRuolo, String operazione) {
        Utente utenteDaModificare = utenteRepository.findById(idUtente)
                .orElseThrow(()-> new RuntimeException("Utente con non trovato"));

        Ruolo ruolo = ruoloRepository.findById(idRuolo) //Ricordarsi di aggiungere la L di Long dopol'id Ruolo.
                .orElseThrow(() -> new RuntimeException("Ruolo di default non trovato"));

        if("aggiungi".equalsIgnoreCase(operazione)){
            if (utenteDaModificare.getRuoli().contains(ruolo)){
                return "L'utente ha già questo ruolo";
            }
            utenteDaModificare.getRuoli().add(ruolo);
            utenteRepository.save(utenteDaModificare);
            return "Ruolo " + ruolo.getNomeRuolo() + " aggiunto all'utente con ID: " + idUtente;
        } else if ("rimuovi".equalsIgnoreCase(operazione)) {
            if (!utenteDaModificare.getRuoli().contains(ruolo)){
                return "L'utente non ha questo ruolo"; //se non ha il ruolo non lo puoi rimuovere.
            }

        }

    }
    public String aggiungiRuolo(Long idUtente, Long idRuolo){


    }

    public String rimuoviRuolo(Long idUtente, Long idRuolo){
        Utente utenteDaModificare = utenteRepository.findById(idUtente).orElseThrow(()-> new RuntimeException("Utente con non trovato"));
        Ruolo nuovoRuolo = ruoloRepository.findById(idRuolo) //Ricordarsi di aggiungere la L di Long dopol'id Ruolo.
                .orElseThrow(() -> new RuntimeException("Ruolo di default non trovato"));
        utenteDaModificare.getRuoli().remove(nuovoRuolo);
        return "Ruolo " + nuovoRuolo.getNomeRuolo() + " tolto all'utente con ID: " + idUtente;
    }

    //CRUD UTENTE GENERICO-------(Vedi UtenteController)---------------------------------------------------------------------------------
    // L'utente semplice può solo:
    // VISUALIZZARE una lista eventi. (Get)
    // PRENOTARE posti agli eventi disponibili (check sulla disponibilità). (Post)
    // (EXTRA) VISUALIZZARE eventi prenotati. (Get)
    // (EXTRA) ANNULLARE la prenotazione. (Delete)

    public String insertUtente(RegistrationRequest utenteRequest) {
        checkDuplicatedKey(utenteRequest.getUsername(), utenteRequest.getEmail());
        Utente utente = new Utente(
                utenteRequest.getUsername(),
                utenteRequest.getEmail(),
                utenteRequest.getPassword()
                /*new Ruolo(ERuolo.ROLE_UTENTE)// Ruolo di default*/
        );
        // Assegno il ruolo di default "ROLE_UTENTE"
        Ruolo ruolo = ruoloRepository.findById(1L) //Ruolo utente ha sempre id 1L.
                .orElseThrow(() -> new RuntimeException("Ruolo di default non trovato"));

        utente.setRuoli(new HashSet<>(Set.of(ruolo)));

        utenteRepository.save(utente);
        return "Utente " + utente.getUsername() + " inserito con ID: " + utente.getId();
    }


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
