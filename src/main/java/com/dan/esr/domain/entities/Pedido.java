package com.dan.esr.domain.entities;

import com.dan.esr.domain.entities.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotal;

    @Column(name = "taxa_frete", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxaFrete;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(name = "data_criacao", columnDefinition = "datetime", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_confirmacao", columnDefinition = "datetime")
    private LocalDateTime dataConfirmacao;

    @Column(name = "data_cancelamento", columnDefinition = "datetime")
    private LocalDateTime dataCancelamento;

    @Column(name = "data_entrega", columnDefinition = "datetime")
    private LocalDateTime dataEntrega;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", foreignKey =
    @ForeignKey(name = "fk_pedido_endereco"), referencedColumnName = "id")
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private StatusPedido statusPedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey =
    @ForeignKey(name = "fk_pedido_usuario"), referencedColumnName = "id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false, foreignKey =
    @ForeignKey(name = "fk_pedido_restaurante"), referencedColumnName = "id")
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "formas_pagamento_id", nullable = false, foreignKey =
    @ForeignKey(name = "fk_pedido_formas_pagamento"), referencedColumnName = "id")
    private FormasDePagamento formasDePagamento;

    @OneToMany(mappedBy = "pedido")
    @ToString.Exclude
    private List<ItemPedido> itensPedidos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido pedido)) return false;
        return Objects.equals(getId(), pedido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Entity
    @Table(name = "itens_pedido", uniqueConstraints =
            {@UniqueConstraint(columnNames = {"pedido_id", "produto_id"}, name = "uk_item_pedido_produto")})
    public static class ItemPedido implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 6)
        private short quantidade;

        @Column(name = "preco_unit", nullable = false, precision = 10, scale = 2)
        private BigDecimal precoUnitario;

        @Column(name = "preco_total", nullable = false, precision = 10, scale = 2)
        private BigDecimal precoTotal;

        @Column(name = "observacao", length = 100)
        private String observacao;

        @OneToOne
        @JoinColumn(name = "produto_id", nullable = false, foreignKey =
        @ForeignKey(name = "fk_item_pedido_produto"), referencedColumnName = "id")
        private Produto produto;

        @ManyToOne
        @JoinColumn(name = "pedido_id", nullable = false, foreignKey =
        @ForeignKey(name = "fk_item_pedido_pedido"), referencedColumnName = "id")
        private Pedido pedido;

    }


}
