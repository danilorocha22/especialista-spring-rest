package com.dan.esr.domain.entities;

import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.grupo.GrupoNaoEncontradoException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "usuarios", schema = "dan_food")
public class Usuario implements Serializable, IdentificavelParaAdicionarOuRemover {
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
    private Set<Grupo> grupos = new HashSet<>();

    public boolean isNovo() {
        return getId() == null;
    }

    public void validarSenhaAtual(String senha) {
        if (isNaoConfere(senha)) {
            throw new NegocioException("A senha atual não confere.");
        }
    }

    public boolean isNaoConfere(String senha) {
        return !isConfere(senha);
    }

    public boolean isConfere(String senha) {
        return getSenha().equals(senha);
    }

    public boolean isDiferente(Usuario usuario) {
        return !isIgual(usuario);
    }

    public boolean isIgual(Usuario usuario) {
        return Objects.equals(getId(), usuario.getId());
    }

    public void adicionar(Grupo grupo) {
        validarUsuarioPossui(grupo, true);
        boolean naoAdicionado = !this.grupos.add(grupo);
        validarAdicionadoOuRemovido(naoAdicionado, grupo, "adicionado");
    }

    public void remover(Grupo grupo) {
        validarUsuarioPossui(grupo, false);
        boolean naoRemovido = !this.grupos.remove(grupo);
        validarAdicionadoOuRemovido(naoRemovido, grupo, "removido");
    }

    private void validarUsuarioPossui(Grupo grupo, boolean possui) {
        boolean contem = this.grupos.contains(grupo);
        if (possui && contem) {
            throw new NegocioException(mensagemErro(grupo, "já existe"));
        } else if (!possui && !contem) {
            throw new GrupoNaoEncontradoException(mensagemErro(grupo, "não existe"));
        }
    }

    private void validarAdicionadoOuRemovido(boolean condicao, Grupo grupo, String msg) {
        if (condicao) {
            throw new NegocioException(mensagemErro(grupo, "não foi ".concat(msg)));
        }
    }

    private String mensagemErro(Grupo grupo, String msg) {
        return (("O grupo com ID %s %s no usuário %s, verifique os dados informados e tente novamente. Se o " +
                "problema persistir, contate o administrador.").formatted(grupo.getId(), msg, this.getNome()));
    }
}