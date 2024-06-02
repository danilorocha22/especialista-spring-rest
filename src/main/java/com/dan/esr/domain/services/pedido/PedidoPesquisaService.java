package com.dan.esr.domain.services.pedido;

import com.dan.esr.core.util.LoggerHelper;
import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.pedido.PedidoNaoEncontrado;
import com.dan.esr.domain.repositories.PedidoRepository;
import com.dan.esr.domain.repositories.filter.PedidoFiltro;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dan.esr.core.util.ValidacaoUtil.validarSeVazio;
import static com.dan.esr.infrastructure.spec.PedidoSpecs.filtrarPedido;

@Service
@RequiredArgsConstructor
public class PedidoPesquisaService {
    private static final LoggerHelper logger = new LoggerHelper(PedidoPesquisaService.class);
    private final PedidoRepository pedidoRepository;

    public Pedido buscarPor(String codigoPedido) {
        return this.pedidoRepository.porCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontrado(codigoPedido));
    }

    public Page<Pedido> filtrarPor(Pageable pageable, PedidoFiltro filtro) {
        try {
            Page<Pedido> pedidoPage = this.pedidoRepository.findAll(filtrarPedido(filtro), pageable);
            validarSeVazio(pedidoPage.getContent());
            return pedidoPage;
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException("Nenhum pedido encontrado.");
        }
    }

    public List<Pedido> todos() {
        try {
            List<Pedido> pedidos = this.pedidoRepository.todosPedidos();
            validarSeVazio(pedidos);
            return pedidos;
        } catch (EntidadeNaoEncontradaException ex) {
            logger.error("todos() -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new PedidoNaoEncontrado(ex.getMessage(), ex);
        }
    }
}