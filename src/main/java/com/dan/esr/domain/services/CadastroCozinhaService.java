package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.ParametroInadequadoException;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.repositories.CozinhaRepository;
import com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.validarCampoObrigatorio;

@RequiredArgsConstructor
@Service
public class CadastroCozinhaService {
    private static final Logger logger = Logger.getLogger(String.valueOf(CadastroCozinhaService.class));
    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha não encontrada";
    public static final String MSG_COZINHA_NAO_ENCONTRADA_COM_NOME = "Cozinha não encontrada com nome: %s";
    public static final String MSG_COZINHA_NAO_ENCONTRADA_COM_ID = "Cozinha não encontrada com ID %s";

    private final CozinhaRepository cozinhaRepo;

    public Cozinha buscarCozinhaPorId(Long id) {
        validarCampoObrigatorio(id, "ID");
        return this.cozinhaRepo.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format(MSG_COZINHA_NAO_ENCONTRADA_COM_ID, id)));
    }

    public List<Cozinha> buscarTodasPorNome(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Cozinha> lista = this.cozinhaRepo.findCozinhasByNomeContains(nome);

        if (lista.isEmpty())
            throw new EntidadeNaoEncontradaException(String.format(
                    MSG_COZINHA_NAO_ENCONTRADA_COM_NOME, nome));

        return lista;
    }

    public Cozinha buscarUnicaPorNome(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        return this.cozinhaRepo.findByNome(nome).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format(MSG_COZINHA_NAO_ENCONTRADA_COM_NOME, nome)));
    }

    public Cozinha salvarOuAtualizar(Cozinha cozinha) {
        validarCampoObrigatorio(cozinha, "Cozinha");

        //cria um novo registro
        if (Objects.isNull(cozinha.getId()))
            return salvar(cozinha);

        //atualiza um registro
        return atualizar(cozinha);
    }

    public void remover(Long id) {
        Cozinha cozinhaRegistro = this.buscarCozinhaPorId(id);
        try {
            this.cozinhaRepo.delete(cozinhaRegistro);
            logger.log(Level.INFO, "Cozinha com ID {0}, removida com sucesso", id);

        } catch (DataIntegrityViolationException e) {
            logger.log(Level.WARNING, "Cozinha com ID {0} não pode ser removida pois possui relacionamento com " +
                    "outra entidade", id);
            throw new EntidadeEmUsoException(
                    String.format("Cozinha com ID %s não pode ser removida pois possui relacionamento com outra " +
                            "entidade", id)
            );
        }
    }

    private Cozinha salvar(Cozinha cozinha) {
        try {
            logger.log(Level.INFO, "Nova cozinha criada: {0}", cozinha.getNome());
            return this.cozinhaRepo.save(cozinha);

        } catch (DataIntegrityViolationException e) {
            logger.log(Level.INFO, "Não foi possível salvar a cozinha: {0}", cozinha.getNome());
            throw new ParametroInadequadoException(String.format(
                    "Não foi possível salvar a cozinha, verifique: %s", cozinha));
        }
    }

    private Cozinha atualizar(Cozinha cozinha) {
        try {
            logger.log(Level.INFO, "Cozinha atualizada com sucesso: {0}", cozinha.getNome());
            return this.cozinhaRepo.saveAndFlush(cozinha);
        } catch (DataIntegrityViolationException e) {
            logger.log(Level.INFO, "Não foi possível atualizar a cozinha: {0}", cozinha.getNome());
            throw new ParametroInadequadoException(String.format(
                    "Não foi possível atualizar a cozinha, verifique: %s", cozinha));
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
