package com.dan.esr.api.models.input.cozinha;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("cozinha")
public class CozinhaInput {
    /*@NotNull(groups = Groups.CozinhaId.class)
    private Long id;*/
    @NotBlank
    private String nome;
}
