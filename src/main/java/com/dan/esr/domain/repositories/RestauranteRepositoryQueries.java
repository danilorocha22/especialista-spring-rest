package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {

    List<Restaurante> find(String nome, BigDecimal freteInicial, BigDecimal freteFinal);

    List<Restaurante> findComFreteGratis(String nome);
}
