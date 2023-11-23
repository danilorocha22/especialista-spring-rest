package com.dan.esr.api.models.input;

import com.dan.esr.core.validation.TaxaFrete;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInput {

    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @TaxaFrete
    private BigDecimal taxaFrete;

    //@ConvertGroup(to = Groups.CozinhaId.class)
    @Valid //Valida em cascata as propriedades da cozinha
    @NotNull
    @JsonProperty("cozinha")
    private CozinhaIdInput cozinhaIdInput;

}
