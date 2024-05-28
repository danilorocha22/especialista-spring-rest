package com.dan.esr.domain.services.cozinha;

import com.dan.esr.core.util.LoggerHelper;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.cozinha.CozinhaNaoPersistidaException;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.repositories.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dan.esr.core.util.MensagensUtil.*;

@Service
@RequiredArgsConstructor
public class CozinhaCadastroService {
    private static final LoggerHelper logger = new LoggerHelper(CozinhaCadastroService.class);
    private final CozinhaConsultaService cozinhaConsulta;
    private final CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvarOuAtualizar(Cozinha cozinha) {
        try {
            return this.cozinhaRepository.salvarOuAtualizar(cozinha).orElseThrow();

        } catch (HibernateException ex) {
            if (cozinha.isNova()) {
                throw new CozinhaNaoPersistidaException(MSG_COZINHA_NAO_SALVA, ex.getCause());
            } else {
                throw new CozinhaNaoPersistidaException(MSG_COZINHA_NAO_ATUALIZADA, ex.getCause());
            }
        }
    }


    @Transactional
    public void remover(Long id) {
        Cozinha cozinha = this.cozinhaConsulta.buscarPor(id);

        try {
            this.cozinhaRepository.remover(cozinha.getId());
            //this.cozinhaRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(MSG_COZINHA_EM_USO.formatted(id), ex);
        }
    }
}