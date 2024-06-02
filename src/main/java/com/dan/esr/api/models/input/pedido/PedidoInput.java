package com.dan.esr.api.models.input.pedido;

import com.dan.esr.api.models.input.endereco.EnderecoInput;
import com.dan.esr.api.models.input.formapagamento.FormaPagamentoIdInput;
import com.dan.esr.api.models.input.itempedido.ItemPedidoInput;
import com.dan.esr.api.models.input.restaurante.RestauranteIdInput;
import com.dan.esr.api.models.input.usuario.UsuarioIdInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    private UsuarioIdInput usuario;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<ItemPedidoInput> itens;
}