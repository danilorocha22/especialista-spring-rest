package com.dan.esr.core.msg;

import com.dan.esr.domain.services.EnvioSmsService;
import com.dan.esr.infrastructure.services.sms.SmsClaroService;
import com.dan.esr.infrastructure.services.sms.SmsVivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.dan.esr.core.msg.SmsProperties.ImplSms;

@Configuration
@RequiredArgsConstructor
public class SmsConfig {
    private final SmsProperties emailProperties;

    @Bean
    public EnvioSmsService envioSmsService() {
        return ImplSms.CLARO.equals(emailProperties.getImpl()) ?
                new SmsClaroService() : new SmsVivoService();
    }
}