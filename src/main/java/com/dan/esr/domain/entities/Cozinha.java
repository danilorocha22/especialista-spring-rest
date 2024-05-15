package com.dan.esr.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "cozinhas", schema = "dan_food")
public class Cozinha implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    //@NotNull(groups = CozinhaId.class)
    //@NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @ToString.Exclude
    @OneToMany(mappedBy = "cozinha",fetch = FetchType.LAZY)
    private List<Restaurante> restaurantes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cozinha cozinha)) return false;
        return Objects.equals(getId(), cozinha.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public boolean isNova() {
        return this.getId() == null;
    }

    public boolean isExiste() {
        return !this.isNova();
    }

}
