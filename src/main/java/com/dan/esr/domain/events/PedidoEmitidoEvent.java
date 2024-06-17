package com.dan.esr.domain.events;

import com.dan.esr.domain.entities.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoEmitidoEvent {
    private Pedido pedido;
}