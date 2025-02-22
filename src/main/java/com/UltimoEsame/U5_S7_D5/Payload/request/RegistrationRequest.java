package com.UltimoEsame.U5_S7_D5.Payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RegistrationRequest {
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 20)
  /*  @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}",
            message = "La password deve contenere almeno una lettera maiuscola, un numero e un carattere speciale")*/
    private String password;

    private Set<String> roles;
}
