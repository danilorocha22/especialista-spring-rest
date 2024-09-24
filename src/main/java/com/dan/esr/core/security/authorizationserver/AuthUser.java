package com.dan.esr.core.security.authorizationserver;

import com.dan.esr.domain.entities.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collection;

@Getter
public class AuthUser extends User {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Long userId;
    private final String fullName;

    public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);
        this.userId = usuario.getId();
        this.fullName = usuario.getEmail();
    }
}