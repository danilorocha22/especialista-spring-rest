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
        @interface PodeConsultar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "hasAuthority('EDITAR_COZINHA')")
        @interface PodeEditar {
        }
    }

    @interface Restaurantes {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "hasAuthority('EDITAR_RESTAURANTES')")
        @interface PodeGerenciarCadastro {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('EDITAR_RESTAURANTES') or " +
                "@danfoodSecurity.gerenciaRestaurante(#id))")
        @interface PodeGerenciarFuncionamento {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and " +
                "isAuthenticated()")
        @interface PodeConsultar {
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
        @interface PodeBuscar {}

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@danfoodSecurity.getUsuarioId() == #filtro.clienteId or"
                + "@danfoodSecurity.gerenciaRestaurante(#filtro.restauranteId))")
        @interface PodePesquisar{}

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("jisAuthenticated()")
        @interface PodeCriar{}

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('GERENCIAR_PEDIDOS') or " +
                "@danfoodSecurity.gerenciaRestauranteDoPedido(#codigoPedido))")
        @interface PodeAlterarStatus {}
    }


    @interface FormasPagamentos{
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @interface PodeConsultar {}

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @interface PodeEditar {}

    }
}
