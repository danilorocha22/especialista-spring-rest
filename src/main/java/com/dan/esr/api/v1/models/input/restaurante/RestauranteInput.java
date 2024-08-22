package com.dan.esr.api.v1.models.input.restaurante;

import com.dan.esr.api.v1.models.input.cozinha.CozinhaIdInput;
import com.dan.esr.api.v1.models.input.endereco.EnderecoInput;
import com.dan.esr.core.validation.TaxaFrete;
import com.dan.esr.core.validation.ValorZeroIncluiDescricao;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    @ApiModelProperty(example = "Thai Gourmet", required = true)
    private String nome;

    //@DecimalMin("0.0")
    //@PositiveOrZero(message = "{TaxaFrete.invalida}")
    @NotNull
    @TaxaFrete
    @ApiModelProperty(example = "R$ 5,00", required = true)
    private BigDecimal taxaFrete;

    //@ConvertGroup(to = Groups.CozinhaId.class)
    //@JsonIgnoreProperties(value = "nome", allowGetters = true)
    @Valid //Valida em cascata as propriedades da cozinha
    @NotNull
    private CozinhaIdInput cozinha;

    @NotNull
    @Valid
    private EnderecoInput endereco;
}