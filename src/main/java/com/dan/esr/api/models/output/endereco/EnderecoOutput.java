package com.dan.esr.api.models.output.endereco;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class EnderecoOutput {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
}