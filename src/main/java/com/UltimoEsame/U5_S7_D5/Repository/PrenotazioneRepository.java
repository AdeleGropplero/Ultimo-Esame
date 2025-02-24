package com.UltimoEsame.U5_S7_D5.Repository;


import com.UltimoEsame.U5_S7_D5.Model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
}
