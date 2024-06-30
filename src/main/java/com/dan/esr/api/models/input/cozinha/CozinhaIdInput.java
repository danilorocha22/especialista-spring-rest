package com.dan.esr.api.models.input.cozinha;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("cozinha")
public class CozinhaIdInput {
    //@NotNull(groups = Groups.CozinhaId.class)
    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long id;
}