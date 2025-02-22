package com.UltimoEsame.U5_S7_D5.Payload.request;

import lombok.Data;

@Data
public class RuoloRequest {
    private String nome;

    public RuoloRequest(String nome) {
        this.nome = nome;
    }
}
