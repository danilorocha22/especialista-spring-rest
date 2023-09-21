package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.EntidadeComAtributoNuloException;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.repositories.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class CadastroCozinhaService {
    private static final Logger logger = Logger.getLogger(String.valueOf(CadastroCozinhaService.class));

    private final CozinhaRepository cozinhaRepository;

    public List<?> buscarPorNome(String nome) {
         List<?> lista = cozinhaRepository.nome(nome);

         if (lista.isEmpty())
             throw new EntidadeNaoEncontradaException(String.format("Não existe cozinha cadastrada com nome: %s", nome));

        return lista;
    }

    public Cozinha adicionarOuAtualizar(Cozinha cozinha) {
        //cria um novo registro
        if (Objects.isNull(cozinha.getId()))
            return adicionar(cozinha);

        //atualiza um registro
        return atualizar(cozinha);
    }

    public void remover(Long id) {
        Cozinha registro = cozinhaRepository.findById(id).orElseThrow(() ->
                new EntidadeNaoEncontradaException(String.format("Não existe cozinha cadastrada com ID %s", id)));
        try {
            cozinhaRepository.delete(registro);
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

    private Cozinha adicionar(Cozinha cozinha) {
        try {
            logger.log(Level.INFO, "Nova cozinha criada: {0}", cozinha.getNome());
            return cozinhaRepository.save(cozinha);

        } catch (DataIntegrityViolationException e) {
            logger.log(Level.INFO, "Não foi possível criar a cozinha: {0}", cozinha.getNome());
            throw new EntidadeComAtributoNuloException(String.format(
                    "Não foi possível adicionar a cozinha, verifique: %s", cozinha));
        }
    }

    private Cozinha atualizar(Cozinha cozinha) {
        try {
            logger.log(Level.INFO, "Cozinha atualizada com sucesso: {0}", cozinha.getNome());
            return cozinhaRepository.saveAndFlush(cozinha);
        } catch (DataIntegrityViolationException e) {
            logger.log(Level.INFO, "Não foi possível atualizar a cozinha: {0}", cozinha.getNome());
            throw new EntidadeComAtributoNuloException(String.format(
                    "Não foi possível atualizar a cozinha, verifique: %s", cozinha));
        }
    }


}
