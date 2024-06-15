package com.dan.esr.domain.services.restaurante;

import com.dan.esr.core.helper.LoggerHelper;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.cidade.CidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.cozinha.CozinhaNaoEncontradaException;
import com.dan.esr.domain.exceptions.restaurante.RestauranteNaoEncontradoException;
import com.dan.esr.domain.exceptions.restaurante.RestauranteNaoPersistidoException;
import com.dan.esr.domain.repositories.RestauranteRepository;
import com.dan.esr.domain.services.cidade.CidadeService;
import com.dan.esr.domain.services.cozinha.CozinhaConsultaService;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dan.esr.core.util.MensagensUtil.*;

@Service
@RequiredArgsConstructor
public class RestauranteCadastroService {
    private static final LoggerHelper logger = new LoggerHelper(RestauranteCadastroService.class);
    private final RestauranteRepository restauranteRepository;
    private final CozinhaConsultaService cozinhaConsulta;
    private final CidadeService cidadeService;

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
}