package com.dan.esr.domain.services.produto;

import com.dan.esr.domain.entities.FotoProduto;
import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.exceptions.produto.FotoProdutoNaoEncontradaException;
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

    public FotoProduto buscarPor(Long restauranteId, Long produtoId) {
        return this.produtoRepository.findFotoBy(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public FotoProduto salvarOuAtualizar(FotoProduto foto, InputStream inputStream) {
        this.produtoRepository.findFotoBy(foto.getRestaurante().getId(), foto.getProduto().getId())
                .ifPresent(this::removerFoto);

        validarFotoProduto(foto);
        FotoProduto novaFoto = this.produtoRepository.salvarOuAtualizar(foto)
                .orElseThrow(() -> new RuntimeException("Ocorreu um erro ao tentar salvar a foto do produto %s: "
                        .formatted(foto.getDescricao())));

        this.produtoRepository.flush();
        this.armazenarFoto(novaFoto, inputStream);
        return novaFoto;
    }

    private void validarFotoProduto(FotoProduto foto) {
        Produto produto = this.produtoService.buscarPor(foto.getRestaurante().getId(), foto.getProduto().getId());
        String novoNome = this.localStorageService.gerarNovoNomeFoto(foto.getNomeArquivo());
        foto.setProduto(produto);
        foto.setNomeArquivo(novoNome);
    }

    private void armazenarFoto(FotoProduto foto, InputStream inputStream) {
        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(inputStream)
                .build();
        this.localStorageService.armazenar(novaFoto);
    }

    @Transactional
    public void removerFoto(FotoProduto foto) {
        this.produtoRepository.removerFoto(foto);
        this.localStorageService.excluir(foto.getNomeArquivo());
    }

    public boolean existeFoto(Long fotoId) {
        return this.produtoRepository.existeFoto(fotoId);
    }
}