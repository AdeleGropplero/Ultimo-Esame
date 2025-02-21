package com.UltimoEsame.U5_S7_D5.Payload.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrenotazioneDTO {

    @NotNull
    private Long utenteId;

    @NotNull
    private Long eventoId;

    @Min(value = 1, message = "Devi prenotare almeno un posto")
    private int nPostiPrenotati;

    @Min(value = 0, message = "La spesa non può essere negativa")
    private double spesa;

}
