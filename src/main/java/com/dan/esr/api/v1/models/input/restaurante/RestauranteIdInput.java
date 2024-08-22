package com.dan.esr.api.v1.models.input.restaurante;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteIdInput {
    @NotNull
    @ApiModelProperty(example = "1", required = true)
    Long id;
}