package com.dan.esr.domain.services.produto;

import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.salvarOuAtualizar(produto)
                .orElseThrow(() -> new EntidadeNaoPersistidaException(
                        "Não foi possível cadastrar o produto %s para o restaurante %s"
                                .formatted(produto.getNome(), produto.getRestaurante().getNome())));
    }

    public Produto buscarPor(Long restauranteId, Long produtoId) {
        return this.produtoRepository.por(produtoId, restauranteId)
                .orElseThrow(() -> new NegocioException("O produto com ID %s não existe no restaurante com ID %s"
                        .formatted(produtoId, restauranteId)));
    }

    public Set<Produto> buscarTodosPor(Boolean ativo, Restaurante restaurante) {
        return this.produtoRepository.todosPor(ativo, restaurante);
    }
}