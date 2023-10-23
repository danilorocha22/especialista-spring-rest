package com.dan.esr.domain.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.*;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@Embeddable
public class Endereco {

    @Column(name = "endereco_logradouro", nullable = false, length = 100)
    private String logradouro;

    @Column(name = "endereco_numero", nullable = false, length = 20)
    private String numero;

    @Column(name = "endereco_complemento", length = 60)
    private String complemento;

    @Column(name = "endereco_bairro", nullable = false, length = 60)
    private String bairro;

    @Column(name = "endereco_cep", nullable = false, length = 9)
    private String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_cidade_id", nullable = false, foreignKey =
    @ForeignKey(name = "fk_endereco_cidade"), referencedColumnName = "id")
    private Cidade cidade;

}
