package com.dan.esr.domain.entities;

import com.dan.esr.core.validation.Groups.EstadoId;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity
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

    public String getCidadeUF() {
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
}