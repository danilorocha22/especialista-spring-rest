package com.dan.esr.api.v2.models.input;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("cozinha")
public class CozinhaInputV2 {
    /*@NotNull(groups = Groups.CozinhaId.class)
    private Long id;*/

    @NotBlank
    @ApiModelProperty(example = "Brasileira", required = true)
    private String nome;
}