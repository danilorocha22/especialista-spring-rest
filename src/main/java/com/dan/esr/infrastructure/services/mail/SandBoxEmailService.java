package com.dan.esr.infrastructure.services.mail;

import com.dan.esr.core.email.EmailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SandBoxEmailService extends SesEnvioEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Override
    MimeMessage criarMimeMessage(Email email) throws MessagingException {
        MimeMessage mimeMessage = super.criarMimeMessage(email);
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        messageHelper.setTo(emailProperties.destinatarioSandbox());
        return mimeMessage;
    }
}