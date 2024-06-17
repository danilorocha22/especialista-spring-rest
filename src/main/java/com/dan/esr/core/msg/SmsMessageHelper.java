package com.dan.esr.core.msg;

import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.services.EnvioSmsService;
import com.dan.esr.domain.services.NotificacaoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsMessageHelper implements NotificacaoClienteService {

    @Autowired
    private EnvioSmsService smsService;

    @Override
    public void pedidoEmitido(Pedido pedido) {
        var sms = EnvioSmsService.Sms.builder()
                .destinatario("+559998765432")
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Emitido")
                .mensagem("O pedido nº %s, foi emitido e está aguardando confirmação.".formatted(pedido.getCodigo()))
                .build();

        this.smsService.enviar(sms);
    }

    @Override
    public void pedidoConfirmado(Pedido pedido) {
        var sms = EnvioSmsService.Sms.builder()
                .destinatario("+559998765432")
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado")
                .mensagem("O pedido nº %s, foi confirmado e está sendo preparado.".formatted(pedido.getCodigo()))
                .build();

        this.smsService.enviar(sms);
    }
}