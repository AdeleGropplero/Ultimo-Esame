package com.UltimoEsame.U5_S7_D5.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "eventi")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titolo;

    private String descrizione;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String luogo;

    @Column(name = "n_posti")
    private int nPosti;

    private Double costoBiglietto;

    @ManyToOne
    @JoinColumn(name = "organizzatore_id")
    private Utente organizzatore;

    public Evento(String titolo, String descrizione, LocalDate data, String luogo, int nPosti, double costoBiglietto, Utente organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.nPosti = nPosti;
        this.costoBiglietto = costoBiglietto;
        this.organizzatore = organizzatore;
    }

}
