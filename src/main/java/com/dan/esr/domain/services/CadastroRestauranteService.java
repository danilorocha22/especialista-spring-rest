package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.RestauranteNaoEncontradoException;
import com.dan.esr.domain.repositories.RestauranteRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.MSG_PROPRIEDADE_NAO_PODE_SER_NULA;
import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.validarCampoObrigatorio;

@RequiredArgsConstructor
@Service
public class CadastroRestauranteService {
    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME = "Restaurante não encontrado com nome: %s";
    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO_COM_ID = "Restaurante não encontrado com ID %s";
    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO_PELO_FRETE = "Restaurante não localizado com as " +
            "taxas informadas: Taxa Inicial: %s; Taxa Final %s";
    private static final String MSG_RESTAURANTE_EM_USO = "Restaurante com ID %s, está em uso com " +
            "e não pode ser excluído";

    private final RestauranteRepository restauranteRepo;

    private final CadastroCozinhaService cozinhaService;

    public Restaurante buscarRestaurantePorId(Long id) {
        validarCampoObrigatorio(id, "ID");
        return this.restauranteRepo.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    public List<Restaurante> buscarRestaurantesPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        validarCampoObrigatorio(taxaInicial, "Taxa Inicial");
        validarCampoObrigatorio(taxaFinal, "Taxa Final");
        List<Restaurante> restaurantes = this.restauranteRepo.findByTaxaFreteBetween(taxaInicial, taxaFinal);

        if (restaurantes.isEmpty()) {
            throw new RestauranteNaoEncontradoException(
                    String.format(MSG_RESTAURANTE_NAO_ENCONTRADO_PELO_FRETE, taxaInicial, taxaFinal)
            );
        }

        return restaurantes;
    }

    public List<Restaurante> consultarPorNomeECozinhaId(String nome, Long cozinhaId) {
        validarCampoObrigatorio(nome, "Nome");
        validarCampoObrigatorio(cozinhaId, "Id da cozinha");
        List<Restaurante> restaurantes = this.restauranteRepo.consultarPorNomeECozinhaId(nome, cozinhaId);

        if (restaurantes.isEmpty()) {
            throw new RestauranteNaoEncontradoException(String.format(
                    MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME + " ou " + MSG_RESTAURANTE_NAO_ENCONTRADO_COM_ID, nome, cozinhaId
            ));
        }

        return restaurantes;
    }

    public List<Restaurante> consultarPorNomeEFrete(String nome, BigDecimal freteInicial, BigDecimal freteFinal) {
        validarCampoObrigatorio(nome, "Nome");
        validarCampoObrigatorio(freteInicial, "Frete Inicial");
        validarCampoObrigatorio(freteFinal, "Frete Final");
        List<Restaurante> restaurantes = this.restauranteRepo.find(nome, freteInicial, freteFinal);

        if (restaurantes.isEmpty()) {
            throw new RestauranteNaoEncontradoException(String.format(
                    MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME, nome
            ));
        }

        return restaurantes;
    }

    public Restaurante buscarPrimeiroPorNome(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        return this.restauranteRepo.findFirstRestauranteByNomeContaining(nome)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(String.format(
                        MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME, nome)));
    }

    public List<Restaurante> buscarTop2PorNome(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Restaurante> restaurantes = this.restauranteRepo.findTop2ByNomeContaining(nome);

        if (restaurantes.isEmpty()) {
            throw new RestauranteNaoEncontradoException(String.format(
                    MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME, nome));
        }

        return restaurantes;
    }

    public List<Restaurante> buscarComFreteGratis(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Restaurante> restaurantes = this.restauranteRepo.findComFreteGratis(nome);

        if (restaurantes.isEmpty()) {
            throw new RestauranteNaoEncontradoException(String.format(
                    MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME, nome));
        }

        return restaurantes;
    }

    public Restaurante buscarPrimeiroRestaurante() {
        return this.restauranteRepo.buscarPrimeiro()
                .orElseThrow(() -> new RestauranteNaoEncontradoException("Restaurante não encontrado"));
    }

    @Transactional
    public Restaurante salvarOuAtualizar(Restaurante restaurante) {
        return (Objects.isNull(restaurante.getId())) ? this.salvar(restaurante) : this.atualizar(restaurante);
    }

    @Transactional
    public void remover(Long id) {
        Restaurante restaurante = this.buscarRestaurantePorId(id);

        try {
            this.restauranteRepo.delete(restaurante);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));
        }
    }

    private Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = this.cozinhaService.buscarCozinhaPorId(cozinhaId);
        restaurante.setCozinha(cozinha);

        return this.restauranteRepo.save(restaurante);
    }

    private Restaurante atualizar(Restaurante restaurante) {
        Restaurante restauranteRegistro = this.buscarRestaurantePorId(restaurante.getId());
        Long cozinhaId = restaurante.getCozinha().getId();
        this.cozinhaService.buscarCozinhaPorId(cozinhaId);
        BeanUtils.copyProperties(restaurante, restauranteRegistro, "id", "formasDePagamento",
                "endereco", "dataCadastro", "produtos");

        return restauranteRepo.saveAndFlush(restauranteRegistro);
    }


}
