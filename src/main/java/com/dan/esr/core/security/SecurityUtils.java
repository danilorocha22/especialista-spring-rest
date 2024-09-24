package com.dan.esr.core.security;

import com.dan.esr.domain.repositories.PedidoRepository;
import com.dan.esr.domain.repositories.RestauranteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class SecurityUtils {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // Obtém o objeto que representa o Token da requisição a partir do contexto
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId()  {
        try {
            Jwt jwt = (Jwt) getAuthentication().getPrincipal();
            return jwt.getClaim("id_usuario");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AccessDeniedException("Acesso negado");
        }
    }

    public boolean gerenciaRestaurante(Long restauranteId)  {
        if (isNull(restauranteId)) {
            return false;
        }
        return this.restauranteRepository.isExisteResponsavel(restauranteId, getUsuarioId());
    }

    public boolean gerenciaRestauranteDoPedido(String codigoPedido)  {
        if (isNull(codigoPedido)) {
            return false;
        }
        return this.pedidoRepository.isRestauranteDoPedido(codigoPedido, getUsuarioId());
    }

    public boolean usuarioAutenticadoIgual(Long usuarioId) {
        return getUsuarioId() != null && usuarioId != null && getUsuarioId().equals(usuarioId);
    }

    public boolean podeGerenciarPedidos(String codigoPedido) {
        return hasAuthority("SCOPE_WRITE") && (hasAuthority("GERENCIAR_PEDIDOS") ||
        this.gerenciaRestauranteDoPedido(codigoPedido));
    }

    private boolean hasAuthority(String authority) {
        return getAuthentication().getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(authority));
    }

    public boolean isAutenticado() {
        return getAuthentication().isAuthenticated();
    }

    public boolean temEscopoEscrita() {
        return hasAuthority("SCOPE_WRITE");
    }

    public boolean temEscopoLeitura() {
        return hasAuthority("SCOPE_READ");
    }

    public boolean podeConsultarRestaurantes() {
        return temEscopoLeitura() && isAutenticado();
    }

    public boolean podeGerenciarCadastroRestaurantes() {
        return temEscopoEscrita() && hasAuthority("EDITAR_RESTAURANTES");
    }

    public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
        return temEscopoEscrita() && (hasAuthority("EDITAR_RESTAURANTES")
                || gerenciaRestaurante(restauranteId));
    }

    public boolean podeConsultarUsuariosGruposPermissoes() {
        return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
    }

    public boolean podeEditarUsuariosGruposPermissoes() {
        return temEscopoEscrita() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
    }

    public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {
        return temEscopoLeitura() && (hasAuthority("CONSULTAR_PEDIDOS")
                || usuarioAutenticadoIgual(clienteId) || gerenciaRestaurante(restauranteId));
    }

    public boolean podePesquisarPedidos() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarFormasPagamento() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarCidades() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarEstados() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarCozinhas() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarEstatisticas() {
        return temEscopoLeitura() && hasAuthority("GERAR_RELATORIOS");
    }
}