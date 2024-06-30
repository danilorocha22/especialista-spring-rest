package com.dan.esr.api.models.output.pedido;

import com.dan.esr.api.models.output.restaurante.RestauranteIdNomeOutput;
import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@ApiModel("Pedido")
@JsonFilter("pedidoFilter") //filtra os campos que serão retornados na resposta
public class PedidoResumoOutput {
    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;

    @ApiModelProperty(example = "CRIADO")
    private String status;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "308.90")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(example = "1")
    private String restauranteId;

    private RestauranteIdNomeOutput restaurante;

    private UsuarioOutput cliente;
}