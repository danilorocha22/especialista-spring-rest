package com.dan.esr.api.v1.models.output.endereco;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
@ApiModel("Endereço")
public class EnderecoOutput {
    @ApiModelProperty(example = "Rua 02 de Julho")
    private String logradouro;

    @ApiModelProperty(example = "754")
    private String numero;

    private String complemento;

    @ApiModelProperty(example = "Alves de Souza")
    private String bairro;

    @ApiModelProperty(example = "48608-165")
    private String cep;

    @ApiModelProperty(example = "1")
    private String cidade;
}