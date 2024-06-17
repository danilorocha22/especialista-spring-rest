package com.dan.esr.infrastructure.services.sms;

import com.dan.esr.domain.services.EnvioSmsService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsVivoService implements EnvioSmsService {

    @Override
    public void enviar(Sms sms) {
        log.info(
                "\n[Vivo SMS]\n Para: {}\n Assunto: {}\n Mensagem: {}",
                sms.getDestinatarios(),
                sms.getAssunto(),
                sms.getMensagem()
        );
    }
}