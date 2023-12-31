package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EstadoNaoEncontradoException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.repositories.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.IfPointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.MSG_PROPRIEDADE_NAO_PODE_SER_NULA;
import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.validarCampoObrigatorio;

@RequiredArgsConstructor
@Service
public class CadastroEstadoService {

    private static final String MSG_ESTADO_EM_USO = "Estado com ID %s, está em uso com " +
            "e não pode ser excluído";

    private final EstadoRepository estadoRepo;

    public Estado buscarEstadoPorId(Long id) {
        validarCampoObrigatorio(id, "ID");
        return this.estadoRepo.findById(id).orElseThrow(() ->
                new EstadoNaoEncontradoException(id));
    }

    public Estado salvarOuAtualizar(Estado estado) {
        try {
            return this.estadoRepo.saveAndFlush(estado);
        } catch (DataIntegrityViolationException e) {
            throw new NegocioException(MSG_PROPRIEDADE_NAO_PODE_SER_NULA, e);
        }
    }

    public void remover(Long id) {
        Estado estadoRegistro = this.buscarEstadoPorId(id);

        try {
            this.estadoRepo.delete(estadoRegistro);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
        }
    }


}
