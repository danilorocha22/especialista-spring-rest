package com.dan.esr.domain.services.pedido;

import com.dan.esr.core.util.LoggerHelper;
import com.dan.esr.domain.entities.*;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.pedido.PedidoNaoEncontrado;
import com.dan.esr.domain.repositories.PedidoRepository;
import com.dan.esr.domain.services.cidade.CidadeService;
import com.dan.esr.domain.services.formaspagamento.FormaPagamentoService;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import com.dan.esr.domain.services.usuario.UsuarioConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dan.esr.core.util.ValidacaoUtil.validarSeVazio;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private static final LoggerHelper logger = new LoggerHelper(PedidoService.class);
    private final RestauranteConsultaService restauranteConsulta;
    private final FormaPagamentoService formaPagamentoService;
    private final CidadeService cidadeService;
    private final UsuarioConsultaService usuarioConsulta;
    private final PedidoRepository pedidoRepository;

    public Pedido buscarPor(String codigoPedido) {
        return this.pedidoRepository.porCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontrado(codigoPedido));
    }

    public List<Pedido> todos() {
        List<Pedido> pedidos = this.pedidoRepository.todos();
        try {
            validarSeVazio(pedidos);
            return pedidos;
        } catch (EntidadeNaoEncontradaException ex) {
            logger.error("todos() -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new PedidoNaoEncontrado(ex.getMessage(), ex);
        }
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        try {
            validarItensPedido(pedido);
            validarPedido(pedido);
            return this.pedidoRepository.salvarOuAtualizar(pedido)
                    .orElseThrow(() -> new EntidadeNaoPersistidaException("Ocorreu um erro ao tentar emitir o " +
                            "pedido com ID %s".formatted(pedido.getId())));

        } catch (NegocioException ex) {
            logger.error("emitir(pedido) -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new NegocioException(ex.getMessage());
        }
    }

    private void validarPedido(Pedido pedido) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(pedido.getRestaurante().getId());
        restaurante.validarSeAberto();
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(pedido.getFormaPagamento().getId());
        restaurante.validarFormaPagamento(formaPagamento);
        Usuario cliente = this.usuarioConsulta.buscarPor(pedido.getUsuario().getId());
        Cidade cidade = this.cidadeService.buscarPor(pedido.getEndereco().getCidade().getId());

        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setUsuario(cliente);
        pedido.getEndereco().setCidade(cidade);
        pedido.calcularTaxaFrete();
        pedido.calcularSubtotal();
        pedido.calcularTotal();
    }

    private void validarItensPedido(Pedido pedido) {
        pedido.getItensPedido().forEach(item -> {
            Long produtoId = item.getProduto().getId();
            Long restauranteId = pedido.getRestaurante().getId();
            Produto produto = this.restauranteConsulta.buscarProduto(restauranteId, produtoId);
            produto.validarDisponibilidade();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.definirPrecoUnitario();
            item.calcularValor();
        });
    }

    @Transactional
    public Pedido confirmar(Pedido pedido) {
        pedido.confirmar();
        return pedido;
    }

    @Transactional
    public Pedido entregar(Pedido pedido) {
        pedido.entregar();
        return pedido;
    }

    @Transactional
    public Pedido cancelar(Pedido pedido) {
        pedido.cancelar();
        return pedido;
    }
}