package com.dan.esr.api.models.input.endereco;

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
    @NotNull private Long cidadeId;
    private String complemento;
}