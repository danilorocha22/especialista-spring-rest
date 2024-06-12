package com.dan.esr.domain.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "album", schema = "dan_food")
public class FotoProduto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "produto_id")
    private Long id;

    private String nomeArquivo;
    private String contentType;
    private Long tamanho;
    private String descricao;

    @MapsId // configura para que a propriedade produto seja mapeada por produto_id
    @OneToOne(fetch = FetchType.LAZY)
    private Produto produto;

    public Restaurante getRestaurante() {
        return this.produto.getRestaurante();
    }
}