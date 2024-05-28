package com.dan.esr.api.models.input.restaurante;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RestauranteIdInput {
    @NotNull Long id;
}