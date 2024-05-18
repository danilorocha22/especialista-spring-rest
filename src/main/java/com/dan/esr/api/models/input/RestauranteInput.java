package com.dan.esr.api.models.input;

import com.dan.esr.core.validation.TaxaFrete;
import com.dan.esr.core.validation.ValorZeroIncluiDescricao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@ValorZeroIncluiDescricao(
        valorField = "taxaFrete",
        descricaoField = "nome",
        descricaoObrigatoria = "Frete Grátis")
public class RestauranteInput {
    @NotBlank
    private String nome;
    //@DecimalMin("0.0")
    //@PositiveOrZero(message = "{TaxaFrete.invalida}")
    @NotNull @TaxaFrete
    private BigDecimal taxaFrete;
    //@ConvertGroup(to = Groups.CozinhaId.class)
    //@JsonIgnoreProperties(value = "nome", allowGetters = true)
    @NotNull @Valid //Valida em cascata as propriedades da cozinha
    private CozinhaIdInput cozinha;
    @NotNull @Valid
    private EnderecoInput endereco;
}