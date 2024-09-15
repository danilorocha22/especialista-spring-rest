package com.dan.esr.core.security;

import com.dan.esr.domain.repositories.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class DanfoodSecurity {

    @Autowired
    private RestauranteRepository restauranteRepository;

    // Obtém o objeto que representa o Token da requisição a partir do contexto
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("id_usuario");
    }

    public boolean gerenciaRestaurante(Long restauranteId) {
        return this.restauranteRepository.existeResponsavel(restauranteId, getUsuarioId());
    }
}