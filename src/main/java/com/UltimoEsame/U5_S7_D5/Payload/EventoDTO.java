package com.UltimoEsame.U5_S7_D5.Payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO {
    @NotBlank(message = "Il titolo non può essere vuoto")
    private String titolo;

    private String descrizione;

    @NotNull(message = "La data non può essere nulla")
    private LocalDate data;

    @NotBlank(message = "Il luogo non può essere vuoto")
    private String luogo;

    @Min(value = 1, message = "Il numero di posti deve essere maggiore di zero")
    private int nPosti;

    @Min(value = 0, message = "Il costo del biglietto non può essere negativo")
    private double costoBiglietto;

    private Long organizzatoreId;
}
