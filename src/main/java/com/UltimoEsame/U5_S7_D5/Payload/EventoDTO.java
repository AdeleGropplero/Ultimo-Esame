package com.UltimoEsame.U5_S7_D5.Payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDTO {
    @NotBlank
    private String titolo;

    private String descrizione;

    @NotBlank
    private LocalDate data;

    @NotBlank
    private String luogo;

    @Min(value = 1, message = "Il numero di posti deve essere maggiore di zero")
    private int nPosti;

    @Min(value = 0, message = "Il costo del biglietto non può essere negativo")
    private double costoBiglietto;

    private Long organizzatoreId;
}
