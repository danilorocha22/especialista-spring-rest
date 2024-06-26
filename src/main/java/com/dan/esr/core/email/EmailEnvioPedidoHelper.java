package com.dan.esr.core.email;

import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.services.EnvioEmailService;
import com.dan.esr.domain.services.EnvioEmailService.Email;
import com.dan.esr.domain.services.NotificacaoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailEnvioPedidoHelper implements NotificacaoClienteService {

    @Autowired
    private EnvioEmailService emailService;

    @Override
    public void pedidoEmitido(Pedido pedido) {
        var mensagem = Email.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Emitido")
                .templateMensagem("pedido-emitido.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        this.emailService.enviar(mensagem);
    }

    @Override
    public void pedidoConfirmado(Pedido pedido) {
        var mensagem = Email.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado")
                .templateMensagem("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        this.emailService.enviar(mensagem);
    }

    @Override
    public void pedidoCancelado(Pedido pedido) {
        var mensagem = Email.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Cancelado")
                .templateMensagem("pedido-cancelado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        this.emailService.enviar(mensagem);
    }
}