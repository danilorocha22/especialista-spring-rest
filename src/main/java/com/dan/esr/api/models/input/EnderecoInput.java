package com.dan.esr.api.models.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EnderecoInput {
    @NotBlank private String logradouro;
    @NotBlank private String numero;
    @NotBlank private String bairro;
    @NotBlank private String cep;
    @NotNull @Valid private CidadeIdInput cidade;
    private String complemento;
}