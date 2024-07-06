package com.dan.esr.api.models.output.pedido;

import com.dan.esr.api.models.output.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@ApiModel("Pedido")
@JsonFilter("pedidoFilter") //filtra os campos que serão retornados na resposta
@Relation(collectionRelation = "pedidos")
@EqualsAndHashCode(of = "codigo", callSuper = false)
public class PedidoResumoOutput extends RepresentationModel<PedidoResumoOutput> {

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

    @JsonProperty("restaurante")
    @ApiModelProperty(value = "Nome do restaurante", example = "Toca do Sabor")
    private String restauranteNome;

    @JsonProperty("formaPagamento")
    @ApiModelProperty(value = "Nome da forma de pagamento", example = "Débito")
    private String formaPagamentoNome;

    @JsonProperty("cliente")
    @ApiModelProperty(value = "Nome do cliente", example = "Joaquim da Silva")
    private String usuarioNome;
}