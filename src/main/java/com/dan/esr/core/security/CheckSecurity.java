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
                "@securityUtils.gerenciaRestaurante(#id))")
        @interface PodeGerenciarFuncionamento {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and " +
                "isAuthenticated()")
        @interface PodeConsultar {
        }
    }

    @interface Pedidos {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize(
                "hasAuthority('CONSULTAR_PEDIDOS') or " +
                        "@securityUtils.getUsuarioId().equals(returnObject.usuario.id) or" +
                        "@securityUtils.gerenciaRestaurante(returnObject.restaurante.id)")
        @interface PodeBuscar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@securityUtils.getUsuarioId() == #filtro.clienteId or"
                + "@securityUtils.gerenciaRestaurante(#filtro.restauranteId))")
        @interface PodePesquisar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("isAuthenticated()")
        @interface PodeCriar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('GERENCIAR_PEDIDOS') or " +
                "@securityUtils.gerenciaRestauranteDoPedido(#codigoPedido))")
        @interface PodeAlterarStatus {
        }
    }


    @interface FormasPagamentos {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @interface PodeConsultar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @interface PodeEditar {
        }
    }

    @interface Estados {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @interface PodeConsultar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @interface PodeEditar {
        }
    }

    @interface Cidades {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @interface PodeConsultar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @interface PodeEditar {
        }
    }

    @interface UsuariosGruposPermissoes {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "@securityUtils.getUsuarioId() == #usuarioId ")
        @interface PodeAlterarPropriaSenha {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or " +
                "@securityUtils.getUsuarioId() == #usuarioId)")
        @interface PodeAlterarUsuario {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        @interface PodeEditar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and " +
                "hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PROMISSOES')")
        @interface PodeConsultar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and " +
                "(hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')) or " +
                "@securityUtils.getUserIdAuthenticated == #usuarioId")
        @interface PodeConsultarUsuario {
        }
    }

    @interface Estatisticas {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and " +
                "hasAuthority('GERAR_RELATORIOS')")
        @interface PodeConsultar {
        }
    }

}