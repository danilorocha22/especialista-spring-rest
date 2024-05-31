package com.dan.esr.domain.services.restaurante;

import com.dan.esr.core.util.LoggerHelper;
import com.dan.esr.domain.entities.*;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.cidade.CidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.cozinha.CozinhaNaoEncontradaException;
import com.dan.esr.domain.exceptions.restaurante.RestauranteNaoEncontradoException;
import com.dan.esr.domain.exceptions.restaurante.RestauranteNaoPersistidoException;
import com.dan.esr.domain.repositories.RestauranteRepository;
import com.dan.esr.domain.services.cidade.CidadeService;
import com.dan.esr.domain.services.cozinha.CozinhaConsultaService;
import com.dan.esr.domain.services.formaspagamento.FormaPagamentoService;
import com.dan.esr.domain.services.usuario.UsuarioConsultaService;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dan.esr.core.util.MensagensUtil.*;
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