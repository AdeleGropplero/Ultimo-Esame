package com.UltimoEsame.U5_S7_D5.Ruolo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuoloService {
    @Autowired
    RuoloRepository ruoloRepository;

    public void insertRuolo(Ruolo ruolo){
        ruoloRepository.save(ruolo);
    }

    public void insertRuoli(List<Ruolo> ruoli) {
        ruoloRepository.saveAll(ruoli);
    }

    public boolean existsByNomeRuolo(ERuolo nomeRuolo) {
        return ruoloRepository.existsByNomeRuolo(nomeRuolo);
    }

    public Ruolo getRuolo(Long id){
        return ruoloRepository.findById(id).orElseThrow();
    }
}
