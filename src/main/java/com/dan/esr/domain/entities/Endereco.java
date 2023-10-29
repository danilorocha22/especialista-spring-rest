package com.dan.esr.domain.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "enderecos")
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

    @ManyToOne
    @JoinColumn(name = "cidade_id", nullable = false, foreignKey =
    @ForeignKey(name = "fk_endereco_cidade"), referencedColumnName = "id")
    private Cidade cidade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco endereco)) return false;
        return Objects.equals(getId(), endereco.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
