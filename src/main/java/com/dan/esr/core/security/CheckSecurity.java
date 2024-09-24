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
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHA')")
        @interface PodeEditar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podeConsultarCozinhas()")
        @interface PodeConsultar {

        }
    }

    @interface Restaurantes {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podeGerenciarCadastroRestaurantes()")
        @interface PodeGerenciarCadastro {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
        @interface PodeGerenciarFuncionamento {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podeConsultarRestaurantes()")
        @interface PodeConsultar {
        }
    }

    @interface Pedidos {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize(
                "hasAuthority('CONSULTAR_PEDIDOS') or " +
                        "@securityUtils.usuarioAutenticadoIgual(returnObject.usuario.id) or " +
                        "@securityUtils.gerenciaRestaurante(returnObject.restaurante.id)")
        @interface PodeBuscar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
        @interface PodePesquisar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @interface PodeCriar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podeGerenciarPedidos(#codigoPedido)")
        @interface PodeGerenciarPedidos {
        }
    }

    @interface FormasPagamentos {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @interface PodeEditar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podeConsultarFormasPagamento()")
        @interface PodeConsultar {
        }
    }

    @interface Estados {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @interface PodeEditar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podeConsultarEstados()")
        @interface PodeConsultar {
        }
    }

    @interface Cidades {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @interface PodeEditar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podeConsultarCidades()")
        @interface PodeConsultar {
        }
    }

    @interface UsuariosGruposPermissoes {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.usuarioAutenticadoIgual(#usuarioId)")
        @interface PodeAlterarPropriaSenha {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or " +
                "@securityUtils.usuarioAutenticadoIgual(#usuarioId))")
        @interface PodeAlterarUsuario {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podeEditarUsuariosGruposPermissoes()")
        @interface PodeEditar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podeConsultarUsuariosGruposPermissoes()")
        @interface PodeConsultar {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and " +
                "(hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')) or " +
                "@securityUtils.usuarioAutenticadoIgual(#usuarioId)")
        @interface PodeConsultarUsuario {
        }
    }

    @interface Estatisticas {
        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("@securityUtils.podeConsultarEstatisticas()")
        @interface PodeConsultar {
        }
    }
}