package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.FotoProduto;

import java.util.Optional;

public interface ProdutoQueries {

    Optional<FotoProduto> salvarOuAtualizar(FotoProduto foto);
    void removerFoto(FotoProduto foto);
    boolean existeFoto(Long fotoId);
}