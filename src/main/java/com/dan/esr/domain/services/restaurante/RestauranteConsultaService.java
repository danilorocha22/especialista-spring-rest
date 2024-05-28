package com.dan.esr.domain.services.restaurante;

import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.produto.ProdutoNaoEncontradoException;
import com.dan.esr.domain.exceptions.restaurante.RestauranteNaoEncontradoException;
import com.dan.esr.domain.repositories.ProdutoRepository;
import com.dan.esr.domain.repositories.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.dan.esr.core.util.MensagensUtil.MSG_RESTAURANTE_NAO_ENCONTRADO;
import static com.dan.esr.core.util.MensagensUtil.MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME;
import static com.dan.esr.core.util.ValidacaoUtil.validarSeVazio;

@Service
@RequiredArgsConstructor
public class RestauranteConsultaService {
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;

    public Restaurante buscarPor(Long id) {
        try {
            return this.restauranteRepository.findById(id)
                    .orElseThrow();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new RestauranteNaoEncontradoException(id);
        }
    }

    public Restaurante buscarPrimeiroRestaurante() {
        return this.restauranteRepository.primeiro()
                .orElseThrow(() -> new RestauranteNaoEncontradoException(MSG_RESTAURANTE_NAO_ENCONTRADO));
    }

    public Restaurante buscarComProdutos(Long id) {
        return this.restauranteRepository.buscarComProdutos(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    public int contarPorCozinhaId(Long cozinhaId) {
        int quantidade = this.restauranteRepository.countByCozinhaId(cozinhaId);

        if (quantidade == 0) {
            throw new RestauranteNaoEncontradoException(MSG_RESTAURANTE_NAO_ENCONTRADO);
        }
        return quantidade;
    }

    public List<Restaurante> buscarTodos() {
        List<Restaurante> restaurantes = this.restauranteRepository.todos();
        validarSeVazio(restaurantes);
        return restaurantes;
    }

    public Restaurante buscarPrimeiroNomeContendo(String nome) {
        return this.restauranteRepository.primeiroComNomeSemelhante(nome)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME
                        .formatted(nome)));
    }

    public List<Restaurante> buscarFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        List<Restaurante> restaurantes = this.restauranteRepository.buscarComFreteEntre(taxaInicial, taxaFinal);
        validarSeVazio(restaurantes);
        return restaurantes;
    }

    public List<Restaurante> buscarNomeContendoEcozinhaId(String nome, Long cozinhaId) {
        List<Restaurante> restaurantes = this.restauranteRepository.buscarComNomeContendoEcozinhaId(nome, cozinhaId);
        validarSeVazio(restaurantes);
        return restaurantes;
    }

    public List<Restaurante> buscarNomeContendoOuFreteEntre(
            String nome,
            BigDecimal freteInicial,
            BigDecimal freteFinal
    ) {
        List<Restaurante> restaurantes = this.restauranteRepository.buscar(nome, freteInicial, freteFinal);
        validarSeVazio(restaurantes);
        return restaurantes;
    }

    public List<Restaurante> buscarTop2NomeContendo(String nome) {
        List<Restaurante> restaurantes = this.restauranteRepository.top2ComNomeSemelhante(nome);
        validarSeVazio(restaurantes);
        return restaurantes;
    }

    public List<Restaurante> buscarNomeContendoEfreteGratis(String nome) {
        List<Restaurante> restaurantes = this.restauranteRepository.buscarComNomeContendoEfreteGratis(nome);
        validarSeVazio(restaurantes);
        return restaurantes;
    }

    public List<Restaurante> buscarNomeContendo(String nome) {
        List<Restaurante> restaurantes = this.restauranteRepository.comNomeSemelhante(nome);
        validarSeVazio(restaurantes);
        return restaurantes;
    }

    public Produto buscarProduto(Long restauranteId, Long produtoId) {
        return this.produtoRepository.buscarPor(produtoId, restauranteId)
                .orElseThrow(()-> new ProdutoNaoEncontradoException(("Não existe produto com ID %s, cadastrado no " +
                        "restaurante com ID %s").formatted(produtoId, restauranteId)));
    }

    public Restaurante buscarPorNomeIgual(String nome) {
        try {
            return this.restauranteRepository.comNomeIgual(nome).orElseThrow();
        } catch (EntidadeNaoEncontradaException ex) {
            throw new RestauranteNaoEncontradoException(MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME.formatted(nome));
        }
    }

    public Restaurante buscarComResponsaveis(Long id) {
        Restaurante restaurante = this.restauranteRepository.buscarComUsuariosResponsaveis(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));

        restaurante.validarResponsaveis();
        return restaurante;
    }
}