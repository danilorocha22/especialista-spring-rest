package com.dan.esr.domain.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Table(name = "formas_de_pagamento", schema = "dan_food")
public class FormaPagamento implements Serializable, IdentificavelParaAdicionarOuRemover {
    @Serial private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nome;

    public boolean isNova() {
        return Objects.isNull(this.id);
    }
}