package com.dan.esr.core.email;

import com.dan.esr.domain.services.EnvioEmailService;
import com.dan.esr.infrastructure.services.mail.FakeEnvioEmailService;
import com.dan.esr.infrastructure.services.mail.SesEnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {
    private final EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        if (EmailProperties.ImplEmail.SES_AWS.equals(emailProperties.getImpl())) {
            return new SesEnvioEmailService();
        } else {
            return new FakeEnvioEmailService();
        }
    }
}