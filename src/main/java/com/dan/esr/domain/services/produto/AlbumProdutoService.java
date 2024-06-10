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

@Service
@RequiredArgsConstructor
public class AlbumProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;
    private final LocalStorageAlbumService localStorageService;

    @Transactional
    public FotoProduto salvarOuAtualizar(FotoProduto foto, InputStream inputStream) {
        Long produtoId = foto.getProduto().getId();
        if (this.existeFoto(produtoId))
            this.removerFoto(produtoId);

        validarFotoProduto(foto);
        FotoProduto novaFoto = this.produtoRepository.salvarOuAtualizar(foto)
                .orElseThrow(() ->
                        new RuntimeException("Ocorreu um erro ao tentar salvar a foto do produto %s: "
                                .formatted(foto.getDescricao())));

        this.armazenarFoto(foto, inputStream);
        return novaFoto;
    }

    public void armazenarFoto(FotoProduto foto, InputStream inputStream) {
        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(inputStream)
                .build();
        this.localStorageService.armazenar(novaFoto);
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
    public void removerFoto(Long fotoId) {
        this.produtoRepository.removerFoto(fotoId);
    }

    public boolean existeFoto(Long fotoId) {
        return this.produtoRepository.existeFoto(fotoId);
    }
}