package com.dan.esr.api.controllers.restaurante;

import com.dan.esr.api.models.input.produto.FotoProdutoInput;
import com.dan.esr.api.models.input.produto.ProdutoInput;
import com.dan.esr.api.models.output.produto.FotoProdutoOutput;
import com.dan.esr.api.models.output.produto.ProdutoOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteProdutosOutput;
import com.dan.esr.core.assemblers.FotoProdutoAssembler;
import com.dan.esr.core.assemblers.ProdutoAssembler;
import com.dan.esr.core.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.FotoProduto;
import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.produto.AlbumProdutoService;
import com.dan.esr.domain.services.produto.ProdutoService;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
    private final RestauranteConsultaService restauranteConsulta;
    private final ProdutoService produtoService;
    private final AlbumProdutoService albumProdutoService;
    private final RestauranteModelAssembler restauranteModelAssembler;
    private final ProdutoAssembler produtoAssembler;
    private final FotoProdutoAssembler fotoProdutoAssembler;

    @GetMapping("/{produtoId}")
    public ProdutoOutput produto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId
    ) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        Produto produto = this.produtoService.buscarPor(restaurante.getId(), produtoId);
        return this.produtoAssembler.toModel(produto);
    }

    @GetMapping
    public RestauranteProdutosOutput produtos(
            @RequestParam(value = "ativos", required = false) Boolean ativos,
            @PathVariable("restauranteId") Long id) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(id);
        Set<Produto> produtos = this.produtoService.buscarTodosPor(ativos, restaurante);
        restaurante.setProdutos(produtos);
        return this.restauranteModelAssembler.toModelProdutos(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteProdutosOutput novoProduto(
            @PathVariable("restauranteId") Long restauranteId,
            @RequestBody @Valid ProdutoInput produtoInput
    ) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        Produto produto = this.produtoAssembler.toDomain(produtoInput);
        produto.setRestaurante(restaurante);
        produto = this.produtoService.salvar(produto);
        restaurante.adicionar(produto);
        return this.restauranteModelAssembler.toModelProdutos(restaurante);
    }

    @PutMapping("/{produtoId}")
    public RestauranteProdutosOutput atualizarProduto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestBody @Valid ProdutoInput produtoInput
    ) {
        Produto produto = this.produtoService.buscarPor(restauranteId, produtoId);
        this.produtoAssembler.copyToDomain(produtoInput, produto);
        produto = this.produtoService.salvar(produto);
        return this.restauranteModelAssembler.toModelProdutos(produto.getRestaurante());
    }

    @PutMapping(value = "/{produtoId}/foto", consumes = MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoOutput atualizarFotoProduto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @Valid FotoProdutoInput fotoProdutoInput
    ) throws IOException {
        FotoProduto foto = this.fotoProdutoAssembler.toDomain(fotoProdutoInput);
        foto.getProduto().setId(produtoId);
        foto.getProduto().setRestaurante(new Restaurante());
        foto.getProduto().getRestaurante().setId(restauranteId);

        return this.fotoProdutoAssembler.toModel(
                this.albumProdutoService.salvarOuAtualizar(foto, fotoProdutoInput.getInputStream())
        );
    }
}