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
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.services.produto.AlbumProdutoService;
import com.dan.esr.domain.services.produto.ProdutoService;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static org.springframework.http.MediaType.*;

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

    @GetMapping(path = "/{produtoId}/foto", produces = APPLICATION_JSON_VALUE)
    public FotoProdutoOutput buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return this.fotoProdutoAssembler.toModel(
                this.albumProdutoService.buscarPor(restauranteId, produtoId)
        );
    }

    @GetMapping(path = "/{produtoId}/foto", produces = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE})
    public ResponseEntity<InputStreamResource> baixarFotoProduto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId
    ) {
        try {
            InputStream inputStream = this.albumProdutoService.baixarFoto(restauranteId, produtoId);
            return ResponseEntity.ok()
                    .contentType(IMAGE_JPEG)
                    .contentType(IMAGE_PNG)
                    .body(new InputStreamResource(inputStream));
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
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