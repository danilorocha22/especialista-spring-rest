package com.dan.esr.api.models.output.endereco;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoOutput {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
}