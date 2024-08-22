package com.dan.esr.api.v1.models.output.restaurante;

import com.dan.esr.api.v1.models.output.produto.ProdutoOutput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Getter @Setter
@ApiModel("Produtos")
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "produtos", itemRelation = "produto")
public class RestauranteProdutosOutput extends EntityModel<RestauranteProdutosOutput> {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Restaurante")
    private String nomeRestaurante;

    private List<ProdutoOutput> produtos;

    /*public RestauranteProdutosOutput(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }*/
}