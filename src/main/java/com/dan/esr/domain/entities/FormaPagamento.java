package com.dan.esr.domain.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static java.util.Objects.isNull;

@Entity
@Getter
@Setter
@ToString(of = "nome")
@EqualsAndHashCode(of = "id")
@Table(name = "formas_de_pagamento", schema = "dan_food")
public class FormaPagamento implements Serializable, IdentificavelParaAdicionarOuRemover {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nome;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataAtualizacao;

    public boolean isNova() {
        return isNull(this.id);
    }
}