package com.dan.esr.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    @interface Cozinhas {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("isAuthenticated()")
        @interface Consultar{}

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('EDITAR_COZINHA')")
        @interface Editar{}
    }
}
