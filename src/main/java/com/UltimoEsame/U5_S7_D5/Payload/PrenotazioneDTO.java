package com.UltimoEsame.U5_S7_D5.Payload;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("nPostiPrenotati")
    @Min(value = 1, message = "Devi prenotare almeno un posto")
    private Integer nPostiPrenotati;

    @Min(value = 0, message = "La spesa non può essere negativa")
    private Double spesa;

    public PrenotazioneDTO(Long utenteId, Long eventoId, Integer nPostiPrenotati) {
        this.utenteId = utenteId;
        this.eventoId = eventoId;
        this.nPostiPrenotati = nPostiPrenotati;
    }
}
