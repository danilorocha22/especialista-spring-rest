package com.dan.esr.core.notification;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("danfood.notificacao.cliente")
public class NotificacaoProperties {

    private ImplNotificacao tipo = ImplNotificacao.EMAIL;

    public enum ImplNotificacao {
        EMAIL, SMS
    }
}