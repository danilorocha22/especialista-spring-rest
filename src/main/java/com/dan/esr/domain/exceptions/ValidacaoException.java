package com.dan.esr.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.io.Serial;

@Getter
@AllArgsConstructor
public class ValidacaoException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private BindingResult bindingResult;
}
