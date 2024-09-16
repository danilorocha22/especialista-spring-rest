package com.dan.esr.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    @interface Cozinhas {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and " +
                "isAuthenticated()")
        @interface Consultar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "hasAuthority('EDITAR_COZINHA')")
        @interface Editar {
        }
    }

    @interface Restaurantes {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "hasAuthority('EDITAR_RESTAURANTES')")
        @interface GerenciarCadastro {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('EDITAR_RESTAURANTES') or " +
                "@danfoodSecurity.gerenciaRestaurante(#id))")
        @interface GerenciarFuncionamento {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and " +
                "isAuthenticated()")
        @interface Consultar {
        }
    }

    @interface Pedidos{
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize(
                "hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@danfoodSecurity.getUsuarioId().equals(returnObject.usuario.id) or" +
                "@danfoodSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
        @interface Buscar{}
    }
}
