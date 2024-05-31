package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Restaurante;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteQueries {
    /*****************   CONSULTAS   ****************/
    Optional<Restaurante> porId(Long id);

    //Optional<Restaurante> buscarRestauranteComProdutos(Long id);

    List<Restaurante> buscar(String nome, BigDecimal freteInicial, BigDecimal freteFinal);

    List<Restaurante> todos();

    List<Restaurante> buscarComNomeContendoEfreteGratis(String nome);

    List<Restaurante> buscarComFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> buscarComNomeContendoEcozinhaId(String nome, Long cozinhaId);

    int countByCozinhaId(Long cozinhaId);
}