package com.dan.esr.api.models.output.pedido;

import com.dan.esr.api.models.output.restaurante.RestauranteIdNomeOutput;
import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@JsonFilter("pedidoFilter") //filtra os campos que serão retornados na resposta
public class PedidoResumoOutput {
    private String codigo;
    private String status;
    private BigDecimal taxaFrete;
    private BigDecimal subtotal;
    private BigDecimal valorTotal;
    private OffsetDateTime dataCriacao;
    private String restauranteId;
    private RestauranteIdNomeOutput restaurante;
    private UsuarioOutput cliente;
}