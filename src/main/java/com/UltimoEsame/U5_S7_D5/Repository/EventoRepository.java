package com.UltimoEsame.U5_S7_D5.Repository;

import com.UltimoEsame.U5_S7_D5.Model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}
