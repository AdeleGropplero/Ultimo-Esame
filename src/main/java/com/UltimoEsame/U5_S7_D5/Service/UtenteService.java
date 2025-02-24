package com.UltimoEsame.U5_S7_D5.Service;

import com.UltimoEsame.U5_S7_D5.Payload.request.LoginRequest;
import com.UltimoEsame.U5_S7_D5.Ruolo.ERuolo;
import com.UltimoEsame.U5_S7_D5.Exception.EmailDuplicateException;
import com.UltimoEsame.U5_S7_D5.Exception.UsernameDuplicateException;
import com.UltimoEsame.U5_S7_D5.Model.Utente;
import com.UltimoEsame.U5_S7_D5.Payload.request.RegistrationRequest;
import com.UltimoEsame.U5_S7_D5.Payload.UtenteDTO;
import com.UltimoEsame.U5_S7_D5.Repository.UtenteRepository;
import com.UltimoEsame.U5_S7_D5.Ruolo.Ruolo;
import com.UltimoEsame.U5_S7_D5.Ruolo.RuoloRepository;
import com.UltimoEsame.U5_S7_D5.Security.JWT.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder passwordEncoder;

/*    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UtenteService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }*/

    @Autowired
    private JwtUtils jwtUtils;

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
    // CREARE eventi. (Post) //✅ gestito in evento service
    // VISUALIZZARE una lista eventi. (Get) //gestito in evento service
    // VISUALIZZARE la lista dei suoi eventi con i posti rimasti. (Get)
    // MODIFICARE i propri eventi. (Patch)
    // CANCELLARE i propri eventi. (Delete)



    //CRUD UTENTE ADMIN-----(Vedi AdminController)-----------------------------------------------------------------------------------
    // L'admin può:
    //✅  MODIFICARE le autorizzazioni degli altri utenti.

    public String modificaRuoloUtente(Long idUtente, Long idRuolo, String operazione) {
        Utente utenteDaModificare = utenteRepository.findById(idUtente)
                .orElseThrow(()-> new RuntimeException("Utente con " + idUtente + " non trovato"));

        Ruolo ruolo = ruoloRepository.findById(idRuolo) //Ricordarsi di aggiungere la L di Long dopol'id Ruolo.
                .orElseThrow(() -> new RuntimeException("Ruolo di default non trovato"));

        if("aggiungi".equalsIgnoreCase(operazione)){
            if (utenteDaModificare.getRuoli().contains(ruolo)){
                return "❌ L'utente ha già questo ruolo";
            }
            utenteDaModificare.getRuoli().add(ruolo);
            //utenteRepository.save(utenteDaModificare); con transactional non serve perchè l'utente è già collegato con il database
            return "✅ Ruolo " + ruolo.getNomeRuolo() + " aggiunto all'utente con ID: " + idUtente;
        } else if ("rimuovi".equalsIgnoreCase(operazione)) {
            if (!utenteDaModificare.getRuoli().contains(ruolo)){
                return "❌ L'utente non ha questo ruolo"; //se non ha il ruolo non lo puoi rimuovere.
            }
            utenteDaModificare.getRuoli().remove(ruolo);

            return "✅ Ruolo " + ruolo.getNomeRuolo() + " tolto all'utente con ID: " + idUtente;
        }else {
            return "⚠️ Operazione non valida! Usa 'aggiungi' o 'rimuovi'.";
        }
    }


    //CRUD UTENTE GENERICO-------(Vedi UtenteController)---------------------------------------------------------------------------------
    // L'utente semplice può solo:
    // VISUALIZZARE una lista eventi. (Get)
    // PRENOTARE posti agli eventi disponibili (check sulla disponibilità). (Post)
    // (EXTRA) VISUALIZZARE eventi prenotati. (Get)
    // (EXTRA) ANNULLARE la prenotazione. (Delete)

    public String insertUtente(RegistrationRequest utenteRequest) {
        checkDuplicatedKey(utenteRequest.getUsername(), utenteRequest.getEmail());
        Utente utente = new Utente();
        utente.setUsername(utenteRequest.getUsername());
        utente.setEmail(utenteRequest.getEmail());
        utente.setPassword(passwordEncoder.encode(utenteRequest.getPassword()));

        // Assegno il ruolo di default "ROLE_UTENTE"
        Ruolo ruolo = ruoloRepository.findById(1L) //Ruolo utente ha sempre id 1L.
                .orElseThrow(() -> new RuntimeException("Ruolo di default non trovato"));

        utente.setRuoli(new HashSet<>(Set.of(ruolo)));

        utenteRepository.save(utente);
        return "Utente " + utente.getUsername() + " inserito con ID: " + utente.getId();
    }


/*    public String checkCredentialsAndGenerateToken(LoginRequest body) {

        Utente found = utenteRepository.findByUsername(body.getUsername()).orElseThrow();
        if (passwordEncoder.matches(body.getPassword(), found.getPassword())) {

            return jwtUtils.createJwtToken((Authentication) found);
        } else {

            throw new RuntimeException("Credenziali errate!");
        }

    }*/


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
