package com.UltimoEsame.U5_S7_D5.Ruolo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ruolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated (EnumType.STRING)
    private ERuolo nomeRuolo;

    public Ruolo(ERuolo nomeRuolo) {
        this.nomeRuolo = nomeRuolo;
    }
    public Ruolo() {}
}
