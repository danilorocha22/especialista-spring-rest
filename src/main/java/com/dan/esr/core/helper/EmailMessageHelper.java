package com.dan.esr.core.helper;

import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.services.EnvioEmailService;
import com.dan.esr.domain.services.EnvioEmailService.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailMessageHelper {
    private final EnvioEmailService emailService;

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