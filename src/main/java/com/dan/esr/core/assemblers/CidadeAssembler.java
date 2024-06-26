package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.cidade.CidadeInput;
import com.dan.esr.api.models.output.cidade.CidadeOutput;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CidadeAssembler {
    private final ModelMapper mapper;

    public CidadeOutput toModel(
            Cidade cidade,
            Class<? extends CidadeOutput> classeDestino
    ) {
        return this.mapper.map(cidade, classeDestino);
    }

    public List<CidadeOutput> toModelList(
            List<Cidade> cidades,
            Class<? extends CidadeOutput> classeDestino
    ) {
        return cidades.stream()
                .map(cidade -> this.toModel(cidade, classeDestino))
                .toList();
    }

    public Cidade toDomain(CidadeInput cidadeInput) {
        return this.mapper.map(cidadeInput, Cidade.class);
    }

    public void copyToCidadeDomain(CidadeInput cidadeInput, Cidade cidade) {
        /* Nova instância de Estado para evitar: org.hibernate.HibernateException: identifier of an instance of
           com.dan.esr.domain.entities.Estado was altered from 2 to 1 */
        cidade.setEstado(new Estado());
        this.mapper.map(cidadeInput, cidade);
    }
}