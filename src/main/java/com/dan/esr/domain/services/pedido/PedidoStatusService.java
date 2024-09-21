package com.dan.esr.domain.services.pedido;

import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoStatusService {
    private final PedidoPesquisaService pedidoPesquisaService;
    private final PedidoRepository pedidoRepository;

    @Transactional
    public Pedido confirmar(String codigoPedido) {
        Pedido pedido = this.pedidoPesquisaService.buscarPor(codigoPedido);
        pedido.confirmar();
        return this.pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido entregar(String codigoPedido) {
        Pedido pedido = this.pedidoPesquisaService.buscarPor(codigoPedido);
        pedido.entregar();
        return pedido;
    }

    @Transactional
    public Pedido cancelar(String codigoPedido) {
        Pedido pedido = this.pedidoPesquisaService.buscarPor(codigoPedido);
        pedido.cancelar();
        return this.pedidoRepository.save(pedido);
    }
}