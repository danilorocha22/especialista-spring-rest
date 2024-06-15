package com.dan.esr.infrastructure.services.mail;

import com.dan.esr.core.email.EmailProperties;
import com.dan.esr.domain.services.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Email email) {
        log.info("Enviando email...");
        log.info("Remetente: %s".formatted(this.emailProperties.getRemetente()));
        log.info("Destinatário(s): %s".formatted(email.getDestinatarios()));
        log.info("Assunto: %s".formatted(email.getAssunto()));
        log.info("Mensagem: %s".formatted(email.getTemplateMensagem()));
    }
}