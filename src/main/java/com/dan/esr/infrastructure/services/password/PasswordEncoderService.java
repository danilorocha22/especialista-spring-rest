package com.dan.esr.infrastructure.services.password;

import com.dan.esr.domain.services.CodificacaoSenhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncoderService implements CodificacaoSenhaService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String criptografar(String rawPassword) {
        return this.passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean equivalente(String rawPassword, String encodedPassword) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }
}