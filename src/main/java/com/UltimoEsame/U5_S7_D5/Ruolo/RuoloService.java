package com.UltimoEsame.U5_S7_D5.Ruolo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuoloService {
    @Autowired
    RuoloRepository ruoloRepository;

    public void insertRuolo(Ruolo ruolo){
        ruoloRepository.save(ruolo);
    }
}
