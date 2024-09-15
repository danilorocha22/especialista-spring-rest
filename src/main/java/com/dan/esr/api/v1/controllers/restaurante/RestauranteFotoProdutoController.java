package com.dan.esr.api.v1.controllers.restaurante;

import com.dan.esr.api.v1.models.input.produto.FotoProdutoInput;
import com.dan.esr.api.v1.models.output.produto.FotoProdutoOutput;
import com.dan.esr.api.v1.openapi.documentation.restaurante.RestauranteFotoProdutoDocumentation;
import com.dan.esr.api.v1.assemblers.FotoProdutoAssembler;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.FotoProduto;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.services.StorageAlbumService;
import com.dan.esr.domain.services.StorageAlbumService.FotoRecuperada;
import com.dan.esr.domain.services.produto.AlbumProdutoService;
import com.dan.esr.infrastructure.services.storage.StorageException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController implements RestauranteFotoProdutoDocumentation {
    private final AlbumProdutoService albumProdutoService;
    private final StorageAlbumService localStorageService;
    private final FotoProdutoAssembler fotoProdutoAssembler;

    @Override
    @CheckSecurity.Restaurantes.Consultar
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public EntityModel<FotoProdutoOutput> buscarDadosFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = this.albumProdutoService.buscarPor(restauranteId, produtoId);
        return EntityModel.of(
                this.fotoProdutoAssembler.toModel(fotoProduto)
        );
    }

    @Override
    @GetMapping(produces = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE})
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
                    .contentType(MediaType.valueOf(fotoProduto.getContentType()))
                    .body(new InputStreamResource(fotoRecuperada.getInputStream()));
        }
    }

    @Override
    @CheckSecurity.Restaurantes.GerenciarFuncionamento
    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public EntityModel<FotoProdutoOutput> atualizarFoto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @Valid FotoProdutoInput fotoProdutoInput,
            @RequestPart MultipartFile arquivo
    ) throws IOException {
        FotoProduto foto = this.fotoProdutoAssembler.toDomain(fotoProdutoInput);
        foto.getProduto().setId(produtoId);
        foto.getProduto().setRestaurante(new Restaurante());
        foto.getProduto().getRestaurante().setId(restauranteId);
        foto = this.albumProdutoService.salvarOuAtualizar(foto, fotoProdutoInput.getInputStream());
        return EntityModel.of(
                this.fotoProdutoAssembler.toModel(foto)
        );
    }

    @Override
    @DeleteMapping
    @CheckSecurity.Restaurantes.GerenciarFuncionamento
    @ResponseStatus(NO_CONTENT)
    public void removerFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        this.albumProdutoService.removerFoto(
                this.albumProdutoService.buscarPor(restauranteId, produtoId)
        );
    }
}