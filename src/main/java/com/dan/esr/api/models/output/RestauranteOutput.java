package com.dan.esr.api.models.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteOutput {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;

    @JsonProperty("cozinha")
    private CozinhaOutput cozinhaOutput;


}
