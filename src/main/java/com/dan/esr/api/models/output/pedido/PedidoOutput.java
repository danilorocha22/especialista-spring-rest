package com.dan.esr.api.models.output.pedido;

import com.dan.esr.api.models.output.endereco.EnderecoOutput;
import com.dan.esr.api.models.output.itempedido.ItemPedidoOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteIdNomeOutput;
import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import com.dan.esr.api.models.output.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoOutput {
    @JsonView(PedidoView.Resumo.class)
    private String codigo;

    @JsonView(PedidoView.Resumo.class)
    private String status;

    @JsonView(PedidoView.Resumo.class)
    private BigDecimal taxaFrete;

    @JsonView(PedidoView.Resumo.class)
    private BigDecimal subtotal;

    @JsonView(PedidoView.Resumo.class)
    private BigDecimal valorTotal;

    @JsonView(PedidoView.Resumo.class)
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;

    @JsonProperty("restaurante")
    @JsonView(PedidoView.Resumo.class)
    private RestauranteIdNomeOutput restaurante;

    @JsonProperty("formaPagamento")
    private String nomeFormaPagamento;

    @JsonProperty("cliente")
    @JsonView(PedidoView.Resumo.class)
    private UsuarioOutput usuario;

    @JsonProperty("enderecoEntrega")
    private EnderecoOutput endereco;

    private List<ItemPedidoOutput> itens;
}