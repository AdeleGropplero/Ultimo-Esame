package com.UltimoEsame.U5_S7_D5.Ruolo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByNomeRuolo(ERuolo nomeRuolo);
}
