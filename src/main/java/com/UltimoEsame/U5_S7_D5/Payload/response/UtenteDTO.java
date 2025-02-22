package com.UltimoEsame.U5_S7_D5.Payload.response;

import com.UltimoEsame.U5_S7_D5.Model.Ruolo.Ruolo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UtenteDTO {

    private String username;

    @NotBlank(message = "Il campo mail non può essere vuoto")
    @Email(message = "Indirizzo email non valido")
    private String email;

    @NotBlank (message = "Il campo password non può essere vuoto")
    private String password;


}
