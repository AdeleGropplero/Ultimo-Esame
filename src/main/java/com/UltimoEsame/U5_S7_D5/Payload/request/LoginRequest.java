package com.UltimoEsame.U5_S7_D5.Payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(min = 3, max = 20)
    private String password;
}
