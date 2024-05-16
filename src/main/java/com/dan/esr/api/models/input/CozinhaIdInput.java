package com.dan.esr.api.models.input;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("cozinha")
public class CozinhaIdInput {
    //@NotNull(groups = Groups.CozinhaId.class)
    @NotNull
    private Long id;
}
