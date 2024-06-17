package com.dan.esr.core.notification;

import com.dan.esr.core.email.EmailEnvioPedidoHelper;
import com.dan.esr.core.msg.SmsMessageHelper;
import com.dan.esr.domain.services.NotificacaoClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.dan.esr.core.notification.NotificacaoProperties.ImplNotificacao;

@Configuration
@RequiredArgsConstructor
public class NotificacaoConfig {
    private final NotificacaoProperties notificacaoProperties;

    @Bean
    public NotificacaoClienteService notificacaoClienteService() {
        return ImplNotificacao.EMAIL.equals(notificacaoProperties.getTipo()) ?
                new EmailEnvioPedidoHelper() : new SmsMessageHelper();
    }
}