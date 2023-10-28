package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.repositories.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.validarCampoObrigatorio;

@AllArgsConstructor
@Service
public class CadastroEstadoService {

    private static final String MSG_ESTADO_EM_USO = "Estado com ID %s, está em uso com " +
            "e não pode ser excluído";
    public static final String MSG_ESTADO_NAO_ENCONTRADO_COM_ID = "Estado não encontrado com ID %s";

    private EstadoRepository estadoRepo;

    public Estado buscarEstadoPorId(Long id) {
        validarCampoObrigatorio(id, "ID");
        return this.estadoRepo.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException(
                String.format(MSG_ESTADO_NAO_ENCONTRADO_COM_ID, id)
        ));
    }

    public Estado salvar(Estado estado) {
        validarCampoObrigatorio(estado, "Estado");
        return this.estadoRepo.save(estado);
    }

    public void remover(Long id) {
        validarCampoObrigatorio(id, "ID");
        Estado estadoRegistro = this.buscarEstadoPorId(id);

        try {
            this.estadoRepo.delete(estadoRegistro);
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
        }
    }




}
