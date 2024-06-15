package com.dan.esr.infrastructure.services.mail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SesEnvioEmailService {

    @Override
    public void enviar(Email email) {
        // Foi necessário alterar o modificador de acesso do método processarTemplate
        // da classe pai para "protected", para poder chamar aqui
        String corpo = processarTemplate(email);

        log.info("[FAKE E-MAIL] Para: {}\n{}", email.getDestinatarios(), corpo);
    }
}