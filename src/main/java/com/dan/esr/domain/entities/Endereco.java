package com.dan.esr.domain.entities;

import javax.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

//@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@ToString
@Entity
@Table(name = "enderecos", schema = "dan_food")
public class Endereco implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String logradouro;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(length = 60)
    private String complemento;

    @Column(nullable = false, length = 60)
    private String bairro;

    @Column(nullable = false, length = 9)
    private String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id", nullable = false, foreignKey =
    @ForeignKey(name = "fk_endereco_cidade"), referencedColumnName = "id")
    private Cidade cidade;
}