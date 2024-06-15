package com.dan.esr.infrastructure.services.mail;

import com.dan.esr.core.email.EmailProperties;
import com.dan.esr.core.helper.LoggerHelper;
import com.dan.esr.domain.services.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Locale;

public class SesEnvioEmailService implements EnvioEmailService {
    private static final LoggerHelper logger = new LoggerHelper(SesEnvioEmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void enviar(Email email) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String mensagem = processarTemplate(email);
            messageHelper.setFrom(emailProperties.getRemetente());
            messageHelper.setTo(getDestinatarios(email));
            messageHelper.setSubject(email.getAssunto());
            messageHelper.setText(mensagem, true);
            this.mailSender.send(messageHelper.getMimeMessage());

        } catch (Exception ex) {
            logger.error("enviar(email) -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new EmailException("Não foi possível enviar o e-mail com o assunto: %s."
                    .formatted(email.getAssunto()), ex);
        }
    }

    private String processarTemplate(@NonNull Email email) {
        try {
            Template template = this.freemarkerConfig.getTemplate(email.getTemplateMensagem(),
                    new Locale("pt", "BR"));

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, email.getVariaveis());
        } catch (Exception ex) {
            throw new EmailException("Não foi possível montar o template do e-mail.", ex);
        }
    }

    private static String[] getDestinatarios(Email email) {
        return email.getDestinatarios().toArray(new String[0]);
    }
}