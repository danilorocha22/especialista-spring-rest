package com.dan.esr.api.models;

import com.dan.esr.api.models.output.CozinhaOutput;
import com.dan.esr.domain.entities.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

//@JsonRootName("cozinhas")
@JacksonXmlRootElement(localName = "cozinhas") //a anotação @JsonRootName("cozinhas") pode ser usada no lugar desta
@AllArgsConstructor
@Data
public class CozinhasXML {

    @JacksonXmlElementWrapper(useWrapping = false) //esta anotação elimina a duplicata de nomes 'cozinhas' no xml
    @JsonProperty("cozinha") //corrige o nome dos itens da lista no xml
    @NotNull
    private List<CozinhaOutput> cozinhas;
}

