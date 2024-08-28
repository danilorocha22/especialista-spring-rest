package com.dan.esr.domain.entities;

import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@ToString
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "itens_pedido",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"pedido_id", "produto_id"},
                name = "uk_item_pedido_produto")})
public class ItemPedido implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 6)
    private short quantidade;

    @Setter
    @Column(name = "preco_unit", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Setter
    @Column(name = "observacao", length = 100)
    private String observacao;

    @Setter
    @OneToOne
    @JoinColumn(
            name = "produto_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_item_pedido_produto"),
            referencedColumnName = "id")
    private Produto produto;

    @Setter
    @ManyToOne
    @JoinColumn(
            name = "pedido_id",
            foreignKey = @ForeignKey(name = "fk_item_pedido_pedido"),
            referencedColumnName = "id")
    private Pedido pedido;

    /*########################################     MÉTODOS     ########################################*/
    public double getValorTotalDouble() {
        return getValorTotal() != null ? getValorTotal().doubleValue() : 0;
    }

    public void calcularValor() {
        if (this.getQuantidade() > 0 && this.getPrecoUnitario() != null) {
            BigDecimal quantidadeBig = BigDecimal.valueOf(quantidade);
            this.valorTotal = quantidadeBig.multiply(precoUnitario);
        }
    }

    public void definirPrecoUnitario() {
        if (this.produto != null && this.produto.getPreco() != null) {
            BigDecimal precoUnit = this.produto.getPreco();
            this.setPrecoUnitario(precoUnit);
        }
    }

    public String nomeProduto() {
        return this.produto != null ? this.produto.getNome() : null;
    }
}