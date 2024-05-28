package com.dan.esr.api.models.output.pedido;

import com.dan.esr.api.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import com.dan.esr.api.models.output.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class PedidoResumoOutput {
    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    @JsonView(RestauranteView.Resumo.class)
    private RestauranteOutput restaurante;
    private UsuarioOutput cliente;
}