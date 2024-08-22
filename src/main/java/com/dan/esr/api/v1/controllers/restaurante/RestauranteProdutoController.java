package com.dan.esr.api.v1.controllers.restaurante;

import com.dan.esr.api.v1.models.input.produto.ProdutoInput;
import com.dan.esr.api.v1.models.output.produto.ProdutoOutput;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteProdutosOutput;
import com.dan.esr.api.v1.openapi.documentation.restaurante.RestauranteProdutoDocumentation;
import com.dan.esr.api.v1.assemblers.ProdutoAssembler;
import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.produto.ProdutoService;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos", produces = APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoDocumentation {
    private final RestauranteConsultaService restauranteService;
    private final ProdutoService produtoService;
    private final ProdutoAssembler produtoAssembler;

    @Override
    @GetMapping("/{produtoId}")
    public EntityModel<ProdutoOutput> produto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId
    ) {
        Restaurante restaurante = this.restauranteService.buscarPor(restauranteId);
        Produto produto = this.produtoService.buscarPor(restaurante.getId(), produtoId);
        return EntityModel.of(
                this.produtoAssembler.toModel(produto)
        );
    }

    @Override
    @GetMapping
    public EntityModel<RestauranteProdutosOutput> produtos(
            @RequestParam(value = "ativos", required = false) Boolean ativos,
            @PathVariable("restauranteId") Long restauranteId) {
        Restaurante restaurante = this.restauranteService.buscarPor(restauranteId);
        List<Produto> produtos = this.produtoService.buscarTodosPor(ativos, restaurante);
        restaurante.setProdutos(produtos);
        return EntityModel.of(
                this.produtoAssembler.toModelProdutos(restaurante)
        );
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<RestauranteProdutosOutput> novoProduto(
            @PathVariable("restauranteId") Long restauranteId,
            @RequestBody @Valid ProdutoInput produtoInput
    ) {
        Restaurante restaurante = this.restauranteService.buscarPor(restauranteId);
        Produto produto = this.produtoAssembler.toDomain(produtoInput);
        produto.setRestaurante(restaurante);
        produto = this.produtoService.salvar(produto);
        restaurante.adicionar(produto);
        return EntityModel.of(
                this.produtoAssembler.toModelProdutos(restaurante)
        );
    }

    @Override
    @PutMapping("/{produtoId}")
    public EntityModel<RestauranteProdutosOutput> atualizarProduto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestBody @Valid ProdutoInput produtoInput
    ) {
        Produto produto = this.produtoService.buscarPor(restauranteId, produtoId);
        this.produtoAssembler.copyToDomain(produtoInput, produto);
        produto = this.produtoService.salvar(produto);
        return EntityModel.of(
                this.produtoAssembler.toModelProdutos(produto.getRestaurante())
        );
    }
}