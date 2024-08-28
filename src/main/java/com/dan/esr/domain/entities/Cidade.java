package com.dan.esr.domain.entities;

import com.dan.esr.core.validation.Groups.EstadoId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@Table(name = "cidades", schema = "dan_food")
public class Cidade implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String nome;

    @Valid
    @NotNull
    @ToString.Exclude
    @ConvertGroup(to = EstadoId.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_cidade_estado"),
            referencedColumnName = "id")
    private Estado estado;

    /*###################################     MÉTODOS      ###################################*/

    public boolean isNova() {
        return getId() == null;
    }

    public String getCidadeEstado() {
        String nomeCompleto = "";
        if (isNomeCidadeValido() && isSiglaEstadoValido()) {
            return nome + "/" + estado.getSigla();
        }
        return nomeCompleto;
    }

    private boolean isNomeCidadeValido() {
        return nome != null && !nome.isBlank();
    }

    private boolean isSiglaEstadoValido() {
        return estado != null && !estado.getSigla().isBlank();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Cidade cidade)) return false;
        return Objects.equals(getId(), cidade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}