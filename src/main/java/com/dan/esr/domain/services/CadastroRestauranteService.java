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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class CadastroRestauranteService {
    private static final Logger logger = Logger.getLogger(String.valueOf(CadastroRestauranteService.class));
    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME = "Restaurante não encontrado com nome: %s";
    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO_COM_ID = "Restaurante não encontrado com ID %s";
    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO_PELO_FRETE = "Restaurante não localizado com as " +
            "taxas informadas: Taxa Inicial: %s; Taxa Final %s";

    public static final String MSG_RESTAURANTE_ESTA_EM_USO_COM_OUTRA_ENTIDADE = "Restaurante com ID %s, está em uso com " +
            "outra entidade e não pode ser excluído";

    private final RestauranteRepository restauranteRepository;

    private final CozinhaRepository cozinhaRepository;

    public Restaurante buscarPorId(Long id) {
        Objects.requireNonNull(id, "ID é obrigatório");
        return this.restauranteRepository.findById(id).orElseThrow(() ->
                new EntidadeNaoEncontradaException(String.format(
                        MSG_RESTAURANTE_NAO_ENCONTRADO_COM_ID, id)));
    }

    public Restaurante salvarOuAtualizar(Restaurante restaurante) {
        // cria um novo registro
        if (Objects.isNull(restaurante.getId())) {
            return salvar(restaurante);
        }

        // atualiza um registro
        return atualizar(restaurante);
    }

    public void remover(Long id) {
        Restaurante restaurante = this.buscarPorId(id);
        try {
            this.restauranteRepository.delete(restaurante);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_ESTA_EM_USO_COM_OUTRA_ENTIDADE, id));
        }
    }

    public List<Restaurante> buscarRestaurantesPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        Objects.requireNonNull(taxaInicial, "Taxa Inicial é obrigatório");
        Objects.requireNonNull(taxaFinal, "Taxa Final é obrigatório");
        List<Restaurante> restaurantes = this.restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);

        if (restaurantes.isEmpty()) throw new EntidadeNaoEncontradaException(
                String.format(MSG_RESTAURANTE_NAO_ENCONTRADO_PELO_FRETE, taxaInicial, taxaFinal)
        );

        return restaurantes;
    }

    private Restaurante salvar(Restaurante restaurante) {
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
                "id", "formasDePagamento", "endereco", "dataCadastro", "produtos");
        logger.log(Level.INFO, "Restaurante atualizado: {0}", restaurante.getNome());
        return restauranteRepository.saveAndFlush(restauranteRegistro);

    }

}
