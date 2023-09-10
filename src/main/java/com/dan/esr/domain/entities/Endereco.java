package com.dan.esr.domain.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Endereco implements Serializable {

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;



}
