package com.dan.esr.api.v1.models.input.produto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProdutoIdInput {
    @NotNull private Long id;
}