package com.dan.esr.domain.services.restaurante;

import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.RestauranteNaoEncontradoException;
import com.dan.esr.domain.repositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.dan.esr.core.util.MessagesUtil.MSG_RESTAURANTE_NAO_ENCONTRADO;
import static com.dan.esr.core.util.MessagesUtil.MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME;

@Service
@RequiredArgsConstructor
public class RestauranteConsultaService {
    private final RestauranteRepository restauranteRepository;

    public Restaurante buscarPorId(Long id) {
        try {
            return this.restauranteRepository.buscarPorId(id)
                    .orElseThrow();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new RestauranteNaoEncontradoException(id);
        }
    }

    public Restaurante buscarPrimeiroRestaurante() {
        return this.restauranteRepository.buscarPrimeira()
                .orElseThrow(() -> new RestauranteNaoEncontradoException(MSG_RESTAURANTE_NAO_ENCONTRADO));
    }

    public Restaurante buscarComProdutos(Long id) {
        try {
            return this.restauranteRepository.buscarRestauranteComProdutos(id)
                    .orElseThrow();
        } catch (RestauranteNaoEncontradoException ex) {
            throw new RestauranteNaoEncontradoException(id);
        }
    }

    public int contarPorCozinhaId(Long cozinhaId) {
        int quantidade = this.restauranteRepository.countByCozinhaId(cozinhaId);

        if (quantidade == 0) {
            throw new RestauranteNaoEncontradoException(MSG_RESTAURANTE_NAO_ENCONTRADO);
        }
        return quantidade;
    }

    public List<Restaurante> buscarTodos() {
        List<Restaurante> restaurantes = this.restauranteRepository.buscarTodosRestaurantes();
        validar(restaurantes);
        return restaurantes;
    }

    public Restaurante buscarPrimeiroNomeContendo(String nome) {
        return this.restauranteRepository.buscarPrimeiraComNomeContendo(nome)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME
                        .formatted(nome)));
    }

    public List<Restaurante> buscarFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        List<Restaurante> restaurantes = this.restauranteRepository.buscarComFreteEntre(taxaInicial, taxaFinal);
        validar(restaurantes);
        return restaurantes;
    }

    public List<Restaurante> buscarNomeContendoEcozinhaId(String nome, Long cozinhaId) {
        List<Restaurante> restaurantes = this.restauranteRepository.buscarComNomeContendoEcozinhaId(nome, cozinhaId);
        validar(restaurantes);
        return restaurantes;
    }

    public List<Restaurante> buscarNomeContendoOuFreteEntre(
            String nome,
            BigDecimal freteInicial,
            BigDecimal freteFinal
    ) {
        List<Restaurante> restaurantes = this.restauranteRepository.buscar(nome, freteInicial, freteFinal);
        validar(restaurantes);
        return restaurantes;
    }

    public List<Restaurante> buscarTop2NomeContendo(String nome) {
        List<Restaurante> restaurantes = this.restauranteRepository.buscarTop2ComNomeContendo(nome);
        validar(restaurantes);
        return restaurantes;
    }

    public List<Restaurante> buscarNomeContendoEfreteGratis(String nome) {
        List<Restaurante> restaurantes = this.restauranteRepository.buscarComNomeContendoEfreteGratis(nome);
        validar(restaurantes);
        return restaurantes;

    }

    public List<Restaurante> buscarNomeContendo(String nome) {
        List<Restaurante> restaurantes = this.restauranteRepository.buscarPorNomeSemelhante(nome);
        validar(restaurantes);
        return restaurantes;
    }

    public Restaurante buscarPorNomeIgual(String nome) {
        try {
            return this.restauranteRepository.buscarPorNomeIgual(nome).orElseThrow();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new RestauranteNaoEncontradoException(MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME.formatted(nome));
        }
    }

    private static void validar(List<Restaurante> restaurantes) {
        if (restaurantes.isEmpty()) {
            throw new RestauranteNaoEncontradoException(MSG_RESTAURANTE_NAO_ENCONTRADO);
        }
    }
}