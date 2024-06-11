package com.dan.esr.domain.services.produto;

import com.dan.esr.domain.entities.FotoProduto;
import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.repositories.ProdutoRepository;
import com.dan.esr.domain.services.LocalStorageAlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

import static com.dan.esr.domain.services.LocalStorageAlbumService.NovaFoto;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class AlbumProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;
    private final LocalStorageAlbumService localStorageService;

    public FotoProduto buscarPor(Long restauranteId, Long produtoId) {
        return this.produtoRepository.findFotoBy(restauranteId, produtoId)
                .orElse(null);
    }

    public FotoProduto buscarPor(Long produtoId) {
        return this.produtoRepository.findFotoBy(produtoId)
                .orElse(null);
    }

    @Transactional
    public FotoProduto salvarOuAtualizar(FotoProduto foto, InputStream inputStream) {
        FotoProduto fotoProduto = this.buscarPor(foto.getRestaurante().getId(), foto.getProduto().getId());
        String nomeArquivoExistente = null;

        if (nonNull(fotoProduto)) {
            nomeArquivoExistente = fotoProduto.getNomeArquivo();
            this.removerFoto(fotoProduto);
        }

        validarFotoProduto(foto);
        FotoProduto novaFoto = this.produtoRepository.salvarOuAtualizar(foto)
                .orElseThrow(() ->
                        new RuntimeException("Ocorreu um erro ao tentar salvar a foto do produto %s: "
                                .formatted(foto.getDescricao())));

        this.produtoRepository.flush();
        this.armazenarFoto(novaFoto, inputStream, nomeArquivoExistente);
        return novaFoto;
    }

    public void armazenarFoto(FotoProduto foto, InputStream inputStream, String nomeArquivoExistente) {
        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(inputStream)
                .build();
        this.localStorageService.substituir(nomeArquivoExistente, novaFoto);
    }

    private void validarFotoProduto(FotoProduto foto) {
        Long produtoId = foto.getProduto().getId();
        Long restauranteId = foto.getProduto().getRestaurante().getId();
        Produto produto = this.produtoService.buscarPor(restauranteId, produtoId);
        String novoNome = this.localStorageService.gerarNomeArquivo(foto.getNomeArquivo());
        foto.setProduto(produto);
        foto.setNomeArquivo(novoNome);
    }

    @Transactional
    public void removerFoto(FotoProduto foto) {
        this.produtoRepository.removerFoto(foto);
    }

    public boolean existeFoto(Long fotoId) {
        return this.produtoRepository.existeFoto(fotoId);
    }
}