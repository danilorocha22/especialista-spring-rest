package com.dan.esr.api.controllers.restaurante;

import com.dan.esr.api.models.input.produto.FotoProdutoInput;
import com.dan.esr.api.models.output.produto.FotoProdutoOutput;
import com.dan.esr.core.assemblers.FotoProdutoAssembler;
import com.dan.esr.domain.entities.FotoProduto;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.services.StorageAlbumService;
import com.dan.esr.domain.services.StorageAlbumService.FotoRecuperada;
import com.dan.esr.domain.services.produto.AlbumProdutoService;
import com.dan.esr.infrastructure.storage.StorageException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {
    private final AlbumProdutoService albumProdutoService;
    private final StorageAlbumService localStorageService;
    private final FotoProdutoAssembler fotoProdutoAssembler;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public FotoProdutoOutput buscarDadosFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return this.fotoProdutoAssembler.toModel(
                this.albumProdutoService.buscarPor(restauranteId, produtoId)
        );
    }

    @GetMapping
    public ResponseEntity<?> baixarFoto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestHeader(name = "accept") String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException {
        try {
            return baixar(restauranteId, produtoId, acceptHeader);

        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        } catch (HttpMediaTypeNotAcceptableException ex) {
            throw new HttpMediaTypeNotAcceptableException(ex.getSupportedMediaTypes());
        } catch (Exception ex) {
            throw new StorageException(ex.getMessage());
        }
    }

    private ResponseEntity<?> baixar(Long restauranteId, Long produtoId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        FotoProduto fotoProduto = this.albumProdutoService.buscarPor(restauranteId, produtoId);
        fotoProduto.validarMediaType(acceptHeader);
        FotoRecuperada fotoRecuperada = this.localStorageService.baixar(fotoProduto.getNomeArquivo());

        return getResponseEntity(fotoRecuperada, fotoProduto);
    }

    private static ResponseEntity<?> getResponseEntity(FotoRecuperada fotoRecuperada, FotoProduto fotoProduto) {
        if (fotoRecuperada.temUrl()) {
            return ResponseEntity
                    .status(FOUND)
                    .header(LOCATION, fotoRecuperada.getUrl())
                    .build();
        } else {
            return ResponseEntity
                    .status(OK)
                    .contentType(valueOf(fotoProduto.getContentType()))
                    .body(new InputStreamResource(fotoRecuperada.getInputStream()));
        }
    }


    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoOutput atualizarFoto(
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

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        this.albumProdutoService.removerFoto(
                this.albumProdutoService.buscarPor(restauranteId, produtoId)
        );
    }
}