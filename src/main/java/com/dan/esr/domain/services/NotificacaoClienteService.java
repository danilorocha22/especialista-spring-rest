package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Pedido;

public interface NotificacaoClienteService {
    void pedidoEmitido(Pedido pedido);
    void pedidoConfirmado(Pedido pedido);
    void pedidoCancelado(Pedido pedido);
}