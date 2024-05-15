package com.dan.esr.api.models.input;

import com.dan.esr.core.validation.Groups;
import com.dan.esr.core.validation.TaxaFrete;
import com.dan.esr.core.validation.ValorZeroIncluiDescricao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.ConvertGroup;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@ValorZeroIncluiDescricao(
        valorField = "taxaFrete",
        descricaoField = "nome",
        descricaoObrigatoria = "Frete Grátis")
public class RestauranteInput {
    private Long id;

    @NotBlank
    private String nome;

    //@DecimalMin("0.0")
    //@PositiveOrZero(message = "{TaxaFrete.invalida}")
    @NotNull
    @TaxaFrete
    private BigDecimal taxaFrete;

    @ConvertGroup(to = Groups.CozinhaId.class)
    @Valid //Valida em cascata as propriedades da cozinha
    @NotNull
    //@JsonProperty("cozinha")
    private CozinhaInput cozinha;


}
