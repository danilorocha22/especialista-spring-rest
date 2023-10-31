package com.dan.esr.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public final class Problema {
    private LocalDateTime dataHora;
    private String mensagem;
    
    /* Métodos */
    public static Problema novoProblema(Throwable e) {
        return Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();
    }


}
