package com.dan.esr.api.models.input.formapagamento;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FormaPagamentoIdInput {
    @NotNull private Long id;
}