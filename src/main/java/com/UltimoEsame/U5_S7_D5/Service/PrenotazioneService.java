package com.UltimoEsame.U5_S7_D5.Service;

import com.UltimoEsame.U5_S7_D5.Repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {
    @Autowired
    PrenotazioneRepository prenotazioneRepository;

    @Autowired
    EventoService eventoService;

    @Autowired
    UtenteService utenteService;


}
