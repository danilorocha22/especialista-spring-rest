package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.exceptions.CidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.repositories.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.MSG_PROPRIEDADE_NAO_PODE_SER_NULA;
import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.validarCampoObrigatorio;

@RequiredArgsConstructor
@Service
public class CadastroCidadeService {
    private static final String MSG_CIDADE_EM_USO = "Cidade com ID %s, está em uso com " +
            "e não pode ser excluído";

    private final CidadeRepository cidadeRepo;

    private final CadastroEstadoService estadoService;

    public Cidade buscarCidadePorId(Long id) {
        validarCampoObrigatorio(id, "ID");
        return this.cidadeRepo.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    public Cidade salvarOuAtualizar(Cidade cidade) {
        validarCampoObrigatorio(cidade.getEstado(), "Estado");
        Long estadoId = cidade.getEstado().getId();
        Estado estadoRegistro = this.estadoService.buscarEstadoPorId(estadoId);
        cidade.setEstado(estadoRegistro);

        try {
            return this.cidadeRepo.saveAndFlush(cidade);
        } catch (DataIntegrityViolationException e) {
            throw new NegocioException(MSG_PROPRIEDADE_NAO_PODE_SER_NULA, e);
        }
    }

    public void remover(Long id) {
        Cidade cidadeRegistro = this.buscarCidadePorId(id);

        try {
            this.cidadeRepo.delete(cidadeRegistro);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
        }
    }


}
