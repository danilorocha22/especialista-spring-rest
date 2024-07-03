package com.dan.esr.domain.services.estado;

import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.estado.EstadoNaoEncontradoException;
import com.dan.esr.domain.repositories.EstadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import static com.dan.esr.core.util.MensagensUtil.MSG_ESTADO_EM_USO;
import static com.dan.esr.core.util.MensagensUtil.MSG_PROPRIEDADE_NAO_PODE_SER_NULA;

@Service
@RequiredArgsConstructor
public class EstadoService {
    private final EstadoRepository estadoRepository;

    public Estado buscarPor(Long id) {
        return this.estadoRepository.findById(id).orElseThrow(() ->
                new EstadoNaoEncontradoException(id));
    }

    @Transactional
    public Estado salvarOuAtualizar(Estado estado) {
        try {
            return this.estadoRepository.saveAndFlush(estado);
        } catch (DataIntegrityViolationException ex) {
            throw new NegocioException(MSG_PROPRIEDADE_NAO_PODE_SER_NULA, ex);
        }
    }

    @Transactional
    public void remover(Long id) {
        Estado estadoRegistro = this.buscarPor(id);

        try {
            this.estadoRepository.delete(estadoRegistro);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(MSG_ESTADO_EM_USO.formatted(id));
        }
    }
}