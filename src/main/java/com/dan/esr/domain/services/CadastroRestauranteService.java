package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.repositories.CozinhaRepository;
import com.dan.esr.domain.repositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class CadastroRestauranteService {
    private static final Logger logger = Logger.getLogger(String.valueOf(CadastroRestauranteService.class));

    private final RestauranteRepository restauranteRepository;

    private final CozinhaRepository cozinhaRepository;


    public Restaurante adicionarOuAtualizar(Restaurante restaurante) {
        // cria um novo registro
        if (Objects.isNull(restaurante.getId())) {
            return adicionar(restaurante);
        }

        // atualiza um registro
        return atualizar(restaurante);
    }

    public void remover(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id).orElse(null);
        try {
            restauranteRepository.delete(Objects.requireNonNull(restaurante));
            logger.log(Level.INFO, "Restaurante removido com sucesso: {0}", restaurante.getNome());

        } catch (DataIntegrityViolationException e) {
            logger.log(Level.SEVERE,
                    "Não é possível remover o restaurante com ID {0}, pois pois relacionamento com outra entidade.", id);
            throw new EntidadeEmUsoException(String.format(
                    "Não é possível remover o restaurante com ID %s, pois possui relacionamento com outra entidade.", id));

        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Não foi possível encontrar o restaurante de ID: {0}", id);
            throw new EntidadeNaoEncontradaException(String.format(
                    "Não foi possível encontrar o restaurante de ID %s", id));
        }
    }

    public List<Restaurante> buscarRestaurantesPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        if (Objects.isNull(taxaInicial) || Objects.isNull(taxaFinal)) {
            throw new EntidadeNaoEncontradaException(String.format(
                    "Não foi possível realizar a pesquisa com as taxas informadas: Taxa Inicial: %s; Taxa Final %s",
                    taxaInicial, taxaFinal
            ));
        }

        List<Restaurante> restaurantes = restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
        if (restaurantes.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format(
                    "Não foi possível encontrar restaurantes com as taxas informadas: " +
                            "Taxa Inicial %s; Taxa Final %s", taxaInicial, taxaFinal));
        return restaurantes;
    }

    private Restaurante adicionar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe cozinha cadastrada com ID %s", cozinhaId)
        ));

        restaurante.setCozinha(cozinha);
        logger.log(Level.INFO, "Novo restaurante criado: {0}", restaurante.getNome());
        return restauranteRepository.save(restaurante);
    }

    private Restaurante atualizar(Restaurante restaurante) {
        Restaurante restauranteRegistro = restauranteRepository.findById(restaurante.getId()).orElseThrow(() ->
                new EntidadeNaoEncontradaException(String.format("Não existe restaurante cadastrado com ID %s",
                        restaurante.getId())));

        Long cozinhaId = restaurante.getCozinha().getId();
        cozinhaRepository.findById(cozinhaId).orElseThrow(() ->
                new EntidadeNaoEncontradaException(String.format("Não existe cozinha cadastrada com ID %s", cozinhaId)));

        BeanUtils.copyProperties(restaurante, restauranteRegistro,
                "id", "formasDePagamento", "endereco");
        logger.log(Level.INFO, "Restaurante atualizado: {0}", restaurante.getNome());
        return restauranteRepository.saveAndFlush(restauranteRegistro);

    }

}
