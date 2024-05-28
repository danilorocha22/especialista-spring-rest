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
public class RestauranteCadastroService {
    private static final LoggerHelper logger = new LoggerHelper(RestauranteCadastroService.class);
    private final RestauranteRepository restauranteRepository;
    private final CozinhaConsultaService cozinhaConsulta;
    private final RestauranteConsultaService restauranteConsulta;
    private final CidadeService cidadeService;
    private final FormaPagamentoService formaPagamentoService;
    private final UsuarioConsultaService usuarioConsulta;

    @Transactional
    public Restaurante salvarOuAtualizar(Restaurante restaurante) {
        try {
            return save(restaurante);

        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
            logger.error("salvarOuAtualizar(restaurante) -> Erro: {}", ex.getLocalizedMessage(), ex.getCause());
            throw new NegocioException(ex.getMessage());

        } catch (HibernateException ex) {
            logger.error("salvarOuAtualizar(restaurante) -> Erro: {}", ex.getLocalizedMessage(), ex.getCause());
            String nome = restaurante.getNome();

            if (restaurante.isNovo()) {
                throw new RestauranteNaoPersistidoException(
                        MSG_RESTAURANTE_NAO_SALVO.formatted(nome), ex.getCause());
            } else {
                throw new RestauranteNaoPersistidoException(
                        MSG_RESTAURANTE_NAO_ATUALIZADO.formatted(nome), ex.getCause());
            }
        }
    }

    private Restaurante save(Restaurante restaurante) {
        prepararRestaurante(restaurante);
        return restauranteRepository.salvarOuAtualizar(restaurante)
                .orElseThrow();
    }

    private void prepararRestaurante(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = this.cozinhaConsulta.buscarPor(cozinhaId);
        restaurante.setCozinha(cozinha);

        Long cidadeId = restaurante.getEndereco().getCidade().getId();
        Cidade cidade = this.cidadeService.buscarPor(cidadeId);
        restaurante.getEndereco().setCidade(cidade);
    }

    @Transactional
    public void remover(Long id) {
        try {
            this.restauranteRepository.remover(id)
                    .orElseThrow(() -> new RestauranteNaoEncontradoException(id));

        } catch (DataIntegrityViolationException ex) {
            logger.error("remover(id) -> Erro: {}", ex.getLocalizedMessage(), ex.getCause());
            throw new EntidadeEmUsoException(MSG_RESTAURANTE_EM_USO.formatted(id), ex);
        }
    }

    @Transactional
    public Restaurante ativar(Long restauranteId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        restaurante.ativar();
        return restaurante;
    }

    @Transactional
    public Restaurante desativar(Long restauranteId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        restaurante.inativar();
        return restaurante;
    }

    @Transactional
    public Restaurante novoProduto(Restaurante restaurante, Produto produto) {
        restaurante.adicionar(produto);
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
    public Restaurante adicionarFormaPagamento(Long restauranteId, Long formasPagamentoId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(formasPagamentoId);
        restaurante.adicionar(formaPagamento);
        return restaurante;
    }

    @Transactional
    public Restaurante removerFormaPagamento(Long restauranteId, Long formasPagamentoId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(formasPagamentoId);
        restaurante.remover(formaPagamento);
        return restaurante;
    }

    @Transactional
    public Restaurante adicionarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        Usuario responsavel = this.usuarioConsulta.buscarPor(usuarioId);
        restaurante.adicionar(responsavel);
        return restaurante;
    }

    @Transactional
    public Restaurante removerResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        Usuario responsavel = this.usuarioConsulta.buscarPor(usuarioId);
        restaurante.remover(responsavel);
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
            ids.forEach(this::desativar);
        } catch (EntidadeNaoEncontradaException ex) {
            logger.error("desativacoes(ids) -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new NegocioException(ex.getMessage());
        }
    }

    /*private static void validarSeVazio(List<Long> ids) {
        if (ids.isEmpty()) {
            throw new NegocioException("A sua solicitação está vazia, informe pelos menos um Id.");
        }
    }*/
}