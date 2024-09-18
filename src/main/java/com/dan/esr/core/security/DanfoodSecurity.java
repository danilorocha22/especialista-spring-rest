package com.dan.esr.core.security;

import com.dan.esr.domain.repositories.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class DanfoodSecurity {

    @Autowired
    private RestauranteRepository restauranteRepository;

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
        return this.restauranteRepository.existeResponsavel(restauranteId, getUsuarioId());
    }
}