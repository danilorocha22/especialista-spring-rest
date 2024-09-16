package com.dan.esr.domain.services.pedido;

import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.pedido.PedidoNaoEncontrado;
import com.dan.esr.domain.filter.PedidoFiltro;
import com.dan.esr.domain.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dan.esr.core.util.ValidacaoUtil.validarSeVazio;
import static com.dan.esr.infrastructure.repositories.spec.PedidoSpecs.filtrarPedido;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoPesquisaService {
    private final PedidoRepository pedidoRepository;

    public Pedido buscarPor(String codigoPedido) {
        return this.pedidoRepository.porCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontrado(codigoPedido));
    }

    public Page<Pedido> filtrarPor(PedidoFiltro filtro, Pageable pageable) {
        return this.pedidoRepository.findAll(filtrarPedido(filtro), pageable);
    }

    public List<Pedido> todos() {
        try {
            return this.pedidoRepository.todosPedidos();
        } catch (Exception ex) {
            log.error("todos() -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}