package com.dan.esr.domain.services.pedido;

import com.dan.esr.core.helper.EmailMessageHelper;
import com.dan.esr.core.helper.LoggerHelper;
import com.dan.esr.domain.entities.*;
import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.repositories.PedidoRepository;
import com.dan.esr.domain.services.cidade.CidadeService;
import com.dan.esr.domain.services.formaspagamento.FormaPagamentoService;
import com.dan.esr.domain.services.produto.ProdutoService;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import com.dan.esr.domain.services.usuario.UsuarioConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoEmissaoService {
    private static final LoggerHelper logger = new LoggerHelper(PedidoEmissaoService.class);
    private final RestauranteConsultaService restauranteConsulta;
    private final FormaPagamentoService formaPagamentoService;
    private final CidadeService cidadeService;
    private final UsuarioConsultaService usuarioConsulta;
    private final ProdutoService produtoService;
    private final PedidoRepository pedidoRepository;
    private final EmailMessageHelper emailHelper;

    @Transactional
    public Pedido emitir(Pedido pedido) {
        try {
            validarItensPedido(pedido);
            validarPedido(pedido);
            Pedido pedidoEmitido = this.pedidoRepository.salvarOuAtualizar(pedido)
                    .orElseThrow(() -> new EntidadeNaoPersistidaException("Ocorreu um erro ao tentar emitir o " +
                            "pedido com ID %s".formatted(pedido.getId())));

            //this.emailHelper.pedidoEmitido(pedidoEmitido);
            return pedidoEmitido;

        } catch (NegocioException ex) {
            logger.error("emitir(pedido) -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new NegocioException(ex.getMessage());
        }
    }

    private void validarPedido(Pedido pedido) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(pedido.getRestaurante().getId());
        restaurante.validarSeAberto();
        //FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(pedido.getFormaPagamento().getId());
        FormaPagamento formaPagamento = pedido.getFormaPagamento();
        restaurante.validarFormaPagamento(formaPagamento);
        Usuario cliente = this.usuarioConsulta.buscarPor(pedido.getCliente().getId());
        Cidade cidade = this.cidadeService.buscarPor(pedido.getEndereco().getCidade().getId());

        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setCliente(cliente);
        pedido.getEndereco().setCidade(cidade);
        pedido.calcularTaxaFrete();
        pedido.calcularSubtotal();
        pedido.calcularTotal();
    }

    private void validarItensPedido(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Long produtoId = item.getProduto().getId();
            Long restauranteId = pedido.getRestaurante().getId();
            Produto produto = this.produtoService.buscarPor(restauranteId, produtoId);
            produto.validarDisponibilidade();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.definirPrecoUnitario();
            item.calcularValor();
        });
    }
}