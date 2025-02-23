package com.UltimoEsame.U5_S7_D5.runner;

import com.UltimoEsame.U5_S7_D5.Model.Utente;
import com.UltimoEsame.U5_S7_D5.Repository.UtenteRepository;
import com.UltimoEsame.U5_S7_D5.Ruolo.ERuolo;
import com.UltimoEsame.U5_S7_D5.Ruolo.Ruolo;
import com.UltimoEsame.U5_S7_D5.Ruolo.RuoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class RunnerApp implements CommandLineRunner {
    @Autowired
    RuoloService ruoloService;

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${utente.admin.username}")
    private String username;

    @Value("${utente.admin.email}")
    private String email;

    @Value("${utente.admin.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        //Qui mi prendo i valori dall'enum in modo programmatico così che se un giorno dovessero cambiare
        //basta cambiare il valore solo nell'enum e non manualmente anche qui.
        //Dopodichè faccio un controllo che se esiste, in caso negativo carica in db il nuovo ruolo.
        //In questo modo al lancio dell'app ho gia il db popolato con i ruoli.
        Arrays.stream(ERuolo.values()).forEach(ruoloEnum -> {
            if(!ruoloService.existsByNomeRuolo(ruoloEnum)){
                Ruolo ruolo = new Ruolo(ruoloEnum);
                ruoloService.insertRuolo(ruolo);
            }
        });

/*
        //Successivamente ai ruoli mi salvo in DB già un utente con ruolo admin che ha
        //accesso a diversi endpoint.
        // Le credenziali dell'admin sono salvate nel properties.
        Utente utente = new Utente(username, email, passwordEncoder.encode(password));
        Ruolo ruolo = ruoloService.getRuolo(3L); // L'admin ha id 3
        utente.setRuoli(new HashSet<>(Set.of(ruolo)));
        utenteRepository.save(utente);*/
    }
}
