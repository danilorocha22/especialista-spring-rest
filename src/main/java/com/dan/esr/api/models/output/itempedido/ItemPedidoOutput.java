package com.dan.esr.api.models.output.itempedido;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
@ApiModel("Itens do Pedido")
@Relation(collectionRelation = "itens")
@EqualsAndHashCode(of = {"produtoId", "nomeProduto", "precoUnitario"}, callSuper = false)
public class ItemPedidoOutput extends RepresentationModel<ItemPedidoOutput> {
    @ApiModelProperty(example = "1")
    private Long produtoId;

    @ApiModelProperty(example = "Bife a Milanesa")
    private String nomeProduto;

    @ApiModelProperty(example = "1")
    private Integer quantidade;

    @ApiModelProperty(example = "R$ 50,00")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "R$ 75,00")
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "Menos picante, por favor")
    private String observacao;
}