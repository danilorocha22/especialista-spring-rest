package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.CozinhaNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.repositories.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.MSG_PROPRIEDADE_NAO_PODE_SER_NULA;
import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.validarCampoObrigatorio;

@RequiredArgsConstructor
@Service
public class CadastroCozinhaService {
    private static final Logger logger = Logger.getLogger(String.valueOf(CadastroCozinhaService.class));
    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha não encontrada";
    public static final String MSG_COZINHA_NAO_ENCONTRADA_COM_NOME = "Cozinha não encontrada com nome: %s";

    public static final String MSG_COZINHA_EM_USO = "Cozinha com ID %s não pode ser removida" +
            " pois possui relacionamento com outra entidade";

    private final CozinhaRepository cozinhaRepo;

    public Cozinha buscarCozinhaPorId(Long id) {
        validarCampoObrigatorio(id, "ID");
        return this.cozinhaRepo.findById(id).orElseThrow(() ->
                new CozinhaNaoEncontradaException(id));
    }

    public List<Cozinha> buscarTodasPorNome(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Cozinha> cozinhas = this.cozinhaRepo.findCozinhasByNomeContains(nome);

        if (cozinhas.isEmpty()) {
            throw new CozinhaNaoEncontradaException(String.format(
                    MSG_COZINHA_NAO_ENCONTRADA_COM_NOME, nome));
        }

        return cozinhas;
    }

    public Cozinha buscarUnicaPorNome(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        return this.cozinhaRepo.findByNome(nome).orElseThrow(() -> new CozinhaNaoEncontradaException(
                String.format(MSG_COZINHA_NAO_ENCONTRADA_COM_NOME, nome)));
    }

    public Cozinha salvarOuAtualizar(Cozinha cozinha) {
        try {
            return this.cozinhaRepo.saveAndFlush(cozinha);
        } catch (DataIntegrityViolationException e) {
            throw new NegocioException(MSG_PROPRIEDADE_NAO_PODE_SER_NULA, e);
        }
    }

    public void remover(Long id) {
        Cozinha cozinhaRegistro = this.buscarCozinhaPorId(id);

        try {
            this.cozinhaRepo.delete(cozinhaRegistro);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, id)
            );
        }
    }

    public Cozinha buscarPrimeiro() {
        return this.cozinhaRepo.buscarPrimeiro()
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        MSG_COZINHA_NAO_ENCONTRADA));
    }

}
