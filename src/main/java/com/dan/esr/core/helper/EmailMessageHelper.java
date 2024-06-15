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
                .templateMensagem("pedido-emitido.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        this.emailService.enviar(mensagem);
    }

    public void pedidoConfirmado(Pedido pedido) {
        var mensagem = Email.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado")
                .templateMensagem("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        this.emailService.enviar(mensagem);
    }
}