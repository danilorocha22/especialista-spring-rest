package com.dan.esr.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "permissoes", schema = "dan_food")
public class Permissao implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nome;

    @Column(nullable = false, length = 80)
    private String descricao;

    public boolean isNova() {
        return getId() == null;
    }
}