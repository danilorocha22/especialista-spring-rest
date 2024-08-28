package com.dan.esr.api.v1.models.input.cozinha;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("cozinha")
public class CozinhaInput {
    /*@NotNull(groups = Groups.CozinhaId.class)
    private Long id;*/

    @NotBlank
    @ApiModelProperty(example = "Brasileira", required = true)
    private String nome;
}