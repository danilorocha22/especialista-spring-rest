package com.dan.esr.api.v1.models.output.produto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@ApiModel("Produto")
@EqualsAndHashCode(of = "id", callSuper = false)
@Relation(collectionRelation = "produtos", itemRelation = "produto")
public class ProdutoOutput extends RepresentationModel<ProdutoOutput> {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Bife acebolado")
    private String nome;

    @ApiModelProperty(example = "Bife acebolado com molho madeira")
    private String descricao;

    @ApiModelProperty(example = "R$ 100,00")
    private BigDecimal preco;

    @ApiModelProperty(example = "true")
    private boolean ativo;
}