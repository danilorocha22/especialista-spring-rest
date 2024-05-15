package com.dan.esr.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios", schema = "dan_food")
public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "usuarios_grupos",
            joinColumns = @JoinColumn(name = "usuario_id",
                    foreignKey = @ForeignKey(name = "fk_usuario_grupo_usuario"),
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id",
                    foreignKey = @ForeignKey(name = "fk_usuario_grupo_grupo"),
                    referencedColumnName = "id"))
    private List<Grupo> grupos = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
