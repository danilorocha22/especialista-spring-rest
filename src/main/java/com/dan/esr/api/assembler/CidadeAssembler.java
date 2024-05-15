package com.dan.esr.api.assembler;

import com.dan.esr.api.models.input.CidadeInput;
import com.dan.esr.api.models.output.CidadeOutput;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Estado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CidadeAssembler {

    private ModelMapper mapper;

    public CidadeOutput toModel(
            Cidade cidade,
            Class<? extends CidadeOutput> classDestino
    ) {
        return this.mapper.map(cidade, classDestino);
    }

    public List<CidadeOutput> toModelList(
            List<Cidade> cidades,
            Class<? extends CidadeOutput> classDestino
    ) {
        return cidades.stream()
                .map(cidade -> this.toModel(cidade, classDestino))
                .toList();
    }

    public Cidade toDomain(CidadeInput cidadeInput) {
        return this.mapper.map(cidadeInput, Cidade.class);
    }

    public List<Cidade> toCollectionDomain(List<CidadeInput> cidadeInputs) {
        return cidadeInputs.stream()
                .map(this::toDomain)
                .toList();
    }

    public void copyToCidadeDomain(CidadeInput cidadeInput, Cidade cidade) {
        cidade.setEstado(new Estado());
        mapper.map(cidadeInput, cidade);
    }

}
