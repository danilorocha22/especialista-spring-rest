package com.dan.esr.domain.listeners;

import com.dan.esr.domain.events.PedidoConfirmadoEvent;
import com.dan.esr.domain.events.PedidoEmitidoEvent;
import com.dan.esr.domain.services.NotificacaoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoListener {

    @Autowired
    private NotificacaoClienteService notificacaoClienteService;

    @TransactionalEventListener
    public void aoEmitirPedido(PedidoEmitidoEvent event) {
        this.notificacaoClienteService.pedidoEmitido(event.getPedido());
    }

    @TransactionalEventListener
    @EventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        this.notificacaoClienteService.pedidoConfirmado(event.getPedido());
    }
}