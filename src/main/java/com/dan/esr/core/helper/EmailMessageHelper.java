package com.dan.esr.core.helper;

import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.services.EnvioEmailService.Email;
import com.dan.esr.infrastructure.services.mail.SesEnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailMessageHelper {
    private final SesEnvioEmailService emailService;

    public void pedidoEmitido(Pedido pedido) {
        var mensagem = Email.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Emitido")
                .mensagem("O pedido de código <strong> " + pedido.getCodigo() + "</strong> foi emitido e " +
                        "precisa ainda ser confirmado.")
                .destinatario(pedido.getUsuario().getEmail())
                .build();

        this.emailService.enviar(mensagem);
    }

    public void pedidoConfirmado(Pedido pedido) {
        var mensagem = Email.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado")
                .mensagem("O pedido de código <strong> " + pedido.getCodigo() + "</strong> foi confirmado.")
                .destinatario(pedido.getUsuario().getEmail())
                .build();

        this.emailService.enviar(mensagem);
    }
}