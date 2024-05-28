package com.dan.esr.api.models.output.endereco;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EnderecoOutput implements Serializable {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
}
