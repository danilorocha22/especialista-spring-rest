package com.dan.esr.api.models.output.pedido;

import com.dan.esr.api.models.output.endereco.EnderecoOutput;
import com.dan.esr.api.models.output.itempedido.ItemPedidoOutput;
import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoOutput {
    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    @JsonProperty("restaurante")
    private String nomeRestaurante;
    @JsonProperty("formaPagamento")
    private String nomeFormaPagamento;
    @JsonProperty("cliente")
    private UsuarioOutput usuario;
    @JsonProperty("enderecoEntrega")
    private EnderecoOutput endereco;
    private List<ItemPedidoOutput> itens;
}