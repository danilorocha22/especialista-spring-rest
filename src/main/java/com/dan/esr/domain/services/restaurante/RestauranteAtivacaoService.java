package com.dan.esr.domain.services.restaurante;

import com.dan.esr.core.helper.LoggerHelper;
import com.dan.esr.domain.entities.*;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dan.esr.core.util.ValidacaoUtil.validarSeVazio;

@Service
@RequiredArgsConstructor
public class RestauranteAtivacaoService {
    private static final LoggerHelper logger = new LoggerHelper(RestauranteAtivacaoService.class);
    private final RestauranteConsultaService restauranteConsulta;

    @Transactional
    public Restaurante ativar(Long restauranteId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        restaurante.ativar();
        return restaurante;
    }

    @Transactional
    public Restaurante inativar(Long restauranteId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        restaurante.inativar();
        return restaurante;
    }

    @Transactional
    public Restaurante abrir(Long id) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(id);
        restaurante.abrir();
        return restaurante;
    }

    @Transactional
    public Restaurante fechar(Long id) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(id);
        restaurante.fechar();
        return restaurante;
    }

    @Transactional
    public void ativacoes(List<Long> ids) {
        try {
            validarSeVazio(ids);
            ids.forEach(this::ativar);
        } catch (EntidadeNaoEncontradaException ex) {
            logger.error("ativacoes(ids) -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new NegocioException(ex.getMessage());
        }
    }

    @Transactional
    public void desativacoes(List<Long> ids) {
        try {
            validarSeVazio(ids);
            ids.forEach(this::inativar);
        } catch (EntidadeNaoEncontradaException ex) {
            logger.error("desativacoes(ids) -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new NegocioException(ex.getMessage());
        }
    }
}