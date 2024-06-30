package com.dan.esr.api.models.output.restaurante;

import com.dan.esr.api.models.output.produto.ProdutoOutput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Produtos")
public class RestauranteProdutosOutput {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Restaurante")
    private String nome;

    private List<ProdutoOutput> produtos;

    /*public RestauranteProdutosOutput(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }*/
}