package com.dan.esr.api.models.output.cozinha;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

//@JsonRootName("cozinhas")
@Data
@AllArgsConstructor
@JacksonXmlRootElement(localName = "cozinhas") //a anotação @JsonRootName("cozinhas") pode ser usada no lugar desta
public class CozinhasXML {

    @NotNull
    @JsonProperty("cozinha") //corrige o nome dos itens da lista no xml
    @JacksonXmlElementWrapper(useWrapping = false) //esta anotação elimina a duplicata de nomes 'cozinhas' no xml
    private List<CozinhaOutput> cozinhas;
}