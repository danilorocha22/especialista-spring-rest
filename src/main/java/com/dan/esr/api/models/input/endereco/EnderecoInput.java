package com.dan.esr.api.models.input.endereco;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {
    @NotBlank
    @ApiModelProperty(example = "Rua 02 de Julho", required = true)
    private String logradouro;

    @NotBlank
    @ApiModelProperty(example = "\"1500\"", required = true)
    private String numero;

    @NotBlank
    @ApiModelProperty(example = "Alves de Souza", required = true)
    private String bairro;

    @NotBlank
    @ApiModelProperty(example = "48608-165", required = true)
    private String cep;

    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long cidadeId;

    private String complemento;
}