package com.dan.esr.domain.entities;

import com.dan.esr.domain.exceptions.NegocioException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@ToString
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Table(name = "produtos", schema = "dan_food")
public class Produto implements Serializable, IdentificavelParaAdicionarOuRemover {
    @Serial private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private boolean ativo = true;

    @ManyToOne()
    @JoinColumn(name = "restaurante_id",
            foreignKey = @ForeignKey(name = "fk_produto_restaurante"),
            referencedColumnName = "id")
    private Restaurante restaurante;

    public void validarDisponibilidade() {
        if (isIndisponivel()) {
            throw new NegocioException("O produto com ID %s, não está disponível no momento."
                    .formatted(id));
        }
    }

    private boolean isIndisponivel() {
        return !ativo;
    }
}