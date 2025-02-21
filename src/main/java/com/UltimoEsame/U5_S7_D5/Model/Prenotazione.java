package com.UltimoEsame.U5_S7_D5.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;


    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    private int nPostiPrenotati;

    private double spesa;

    public Prenotazione(Utente utente, Evento evento, int nPostiPrenotati, double spesa) {
        if (evento == null) {
            throw new IllegalArgumentException("L'evento non può essere null");
        }
        this.utente = utente;
        this.evento = evento;
        this.nPostiPrenotati = nPostiPrenotati;
        this.spesa = nPostiPrenotati * (evento.getCostoBiglietto() != null ? evento.getCostoBiglietto() : 0.0);
    }
}
