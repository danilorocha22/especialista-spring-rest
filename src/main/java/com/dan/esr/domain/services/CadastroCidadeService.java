package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.ParametroInadequadoException;
import com.dan.esr.domain.repositories.CidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.validarCampoObrigatorio;

@AllArgsConstructor
@Service
public class CadastroCidadeService {
    private static final String MSG_CIDADE_EM_USO = "Cidade com ID %s, está em uso com " +
            "e não pode ser excluído";
    public static final String MSG_CIDADE_NAO_ENCONTRADA_COM_ID = "Cidade não encontrada com ID %s";

    private CidadeRepository cidadeRepo;

    private CadastroEstadoService estadoService;

    public Cidade buscarCidadePorId(Long id) {
        validarCampoObrigatorio(id, "ID");
        return this.cidadeRepo.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format(MSG_CIDADE_NAO_ENCONTRADA_COM_ID, id)));
    }

    public Cidade salvar(Cidade cidade) {
        validarCampoObrigatorio(cidade, "Cidade");
        validarCampoObrigatorio(cidade.getEstado(), "Estado");

        Long estadoId = cidade.getEstado().getId();
        Estado estadoRegistro = this.estadoService.buscarEstadoPorId(estadoId);
        cidade.setEstado(estadoRegistro);

        return this.cidadeRepo.saveAndFlush(cidade);
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
