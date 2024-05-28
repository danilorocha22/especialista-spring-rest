package com.dan.esr.domain.entities;

import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.permissao.PermissaoNaoEncontradoException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grupos", schema = "dan_food")
public class Grupo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nome;

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "grupos_permissoes",
            joinColumns = @JoinColumn(name = "grupo_id",
                    foreignKey = @ForeignKey(name = "fk_grupo_permissoes_grupo"),
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id",
                    foreignKey = @ForeignKey(name = "fk_grupo_permissoes_permissoes"),
                    referencedColumnName = "id"))
    private Set<Permissao> permissoes = new HashSet<>();

    public boolean isNova() {
        return getId() == null;
    }

    public void adicionar(Permissao permissao) {
        validarGrupoPossui(permissao, true);
        boolean naoAdicionado = !this.permissoes.add(permissao);
        validarAdicionadoOuRemovido(naoAdicionado, permissao, "adicionada");
    }

    public void remover(Permissao permissao) {
        validarGrupoPossui(permissao, false);
        boolean naoRemovido = !this.permissoes.removeIf(p -> p.getId().equals(permissao.getId()));
        validarAdicionadoOuRemovido(naoRemovido, permissao, "removida");
    }

    private void validarGrupoPossui(Permissao permissao, boolean possui) {
        boolean contem = this.permissoes.contains(permissao);
        if (possui && contem) {
            throw new NegocioException(mensagemErro(permissao, "já exite"));
        } else if (!possui && !contem) {
            throw new PermissaoNaoEncontradoException(mensagemErro(permissao, "não existe"));
        }
    }

    private void validarAdicionadoOuRemovido(boolean condicao, Permissao permissao, String msg) {
        if (condicao) {
            throw new NegocioException(mensagemErro(permissao, "não foi ".concat(msg)));
        }
    }

    private String mensagemErro(Permissao permissao, String msg) {
        return ("A permissão com ID %s %s no grupo %s, verifique os dados informados e tente novamente. Se o " +
                "problema persistir, contate o administrador.").formatted(permissao.getId(), msg, this.getNome());
    }
}