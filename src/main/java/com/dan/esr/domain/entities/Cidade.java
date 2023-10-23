package com.dan.esr.domain.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cidades")
public class Cidade implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false, foreignKey =
    @ForeignKey(name = "fk_cidade_estado"), referencedColumnName = "id")
    private Estado estado;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cidade cidade)) return false;
        return Objects.equals(getId(), cidade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
