package com.dan.esr.domain.services.produto;

import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.produto.ProdutoNaoEncontradoException;
import com.dan.esr.domain.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public Produto buscarPor(Long produtoId, Long restauranteId) {
        return this.produtoRepository.buscarPor(produtoId, restauranteId)
                .orElseThrow(() -> new NegocioException("O produto com ID %s não existe no restaurante com ID %s"
                        .formatted(produtoId, restauranteId)));
    }
}