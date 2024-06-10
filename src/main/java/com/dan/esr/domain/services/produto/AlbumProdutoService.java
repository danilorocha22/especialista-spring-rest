package com.dan.esr.domain.services.produto;

import com.dan.esr.domain.entities.FotoProduto;
import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlbumProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;

    @Transactional
    public FotoProduto salvarOuAtualizar(FotoProduto foto) {
        validarProduto(foto);
        return this.produtoRepository.salvarOuAtualizar(foto)
                .orElseThrow(() -> new NegocioException("Não foi possível salvar a foto: %s"
                        .formatted(foto.getDescricao())));
    }

    private void validarProduto(FotoProduto foto) {
        Long produtoId = foto.getProduto().getId();
        Long restauranteId = foto.getProduto().getRestaurante().getId();
        Produto produto = this.produtoService.buscarPor(restauranteId, produtoId);
        foto.setProduto(produto);
    }

    @Transactional
    public void removerFoto(Long fotoId) {
        this.produtoRepository.removerFoto(fotoId);
    }

    public boolean existeFoto(Long fotoId) {
        return this.produtoRepository.existeFoto(fotoId);
    }
}