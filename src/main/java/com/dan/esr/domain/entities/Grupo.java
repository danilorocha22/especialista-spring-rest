package com.dan.esr.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grupos")
public class Grupo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nome;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "grupos_permissoes",
            joinColumns = @JoinColumn(name = "grupo_id", foreignKey =
            @ForeignKey(name = "fk_grupo_permissoes_grupo"), referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id", foreignKey =
            @ForeignKey(name = "fk_grupo_permissoes_permissoes"), referencedColumnName = "id"))
    @ToString.Exclude
    private List<Permissao> permissoes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grupo grupo)) return false;
        return Objects.equals(getId(), grupo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
