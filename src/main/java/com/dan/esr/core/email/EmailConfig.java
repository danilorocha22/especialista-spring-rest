package com.dan.esr.core.email;

import com.dan.esr.domain.services.EnvioEmailService;
import com.dan.esr.infrastructure.services.email.FakeEnvioEmailService;
import com.dan.esr.infrastructure.services.email.SandBoxEmailService;
import com.dan.esr.infrastructure.services.email.SesEnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {
    private final EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        return switch (emailProperties.getImpl()) {
            case SMTP_ASW -> new SesEnvioEmailService();
            case SANDBOX -> new SandBoxEmailService();
            default -> new FakeEnvioEmailService();
        };
    }
}