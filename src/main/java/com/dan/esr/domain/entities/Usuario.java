package com.dan.esr.domain.entities;

import com.dan.esr.domain.exceptions.NegocioException;
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
@ToString(exclude = { "grupos" })
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios", schema = "dan_food")
public class Usuario implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Transient
    private String novaSenha;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dataCadastro;

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

    public boolean isNovo() {
        return getId() == null;
    }

    public void validarSenhaAtual(String senha) {
        if (isSenhaNaoConfere(senha)) {
            throw new NegocioException("A senha atual não confere.");
        }
    }

    public boolean isSenhaNaoConfere(String senha) {
        return !isSenhaConfere(senha);
    }

    public boolean isSenhaConfere(String senha) {
        return getSenha().equals(senha);
    }

    public boolean isDiferente(Usuario usuario) {
        return !isIgual(usuario);
    }

    public boolean isIgual(Usuario usuario) {
        return Objects.equals(getId(), usuario.getId());
    }
}