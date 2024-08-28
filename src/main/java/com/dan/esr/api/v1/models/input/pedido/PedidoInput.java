package com.dan.esr.api.v1.models.input.pedido;

import com.dan.esr.api.v1.models.input.endereco.EnderecoInput;
import com.dan.esr.api.v1.models.input.formapagamento.FormaPagamentoIdInput;
import com.dan.esr.api.v1.models.input.itempedido.ItemPedidoInput;
import com.dan.esr.api.v1.models.input.restaurante.RestauranteIdInput;
import com.dan.esr.api.v1.models.input.usuario.UsuarioIdInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoInput {
    @Valid
    @NotNull
    private RestauranteIdInput restaurante;

    @Valid
    @NotNull
    private FormaPagamentoIdInput formaPagamento;

    @Valid
    @NotNull
    @JsonProperty("enderecoEntrega")
    private EnderecoInput endereco;

    private UsuarioIdInput cliente;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<ItemPedidoInput> itens;
}