package com.dan.esr.api.models.mixin;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Endereco;
import com.dan.esr.domain.entities.FormasDePagamento;
import com.dan.esr.domain.entities.Produto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

//@JsonInclude(NON_NULL)
public abstract class RestauranteMixin {

    @JsonIgnoreProperties(value = "tipo", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    //@JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private OffsetDateTime dataCadastro;

    //@JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    private List<FormasDePagamento> formasDePagamento;

    @JsonIgnore
    private List<Produto> produtos;

}
