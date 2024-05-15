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

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "pedidos", schema = "dan_food")
public class Pedido implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Setter
    @Column(name = "taxa_frete", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxaFrete;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Setter
    @CreationTimestamp
    @Column(name = "data_criacao", columnDefinition = "datetime", nullable = false)
    private LocalDateTime dataCriacao;

    @Setter
    @Column(name = "data_confirmacao", columnDefinition = "datetime")
    private LocalDateTime dataConfirmacao;

    @Setter
    @Column(name = "data_cancelamento", columnDefinition = "datetime")
    private LocalDateTime dataCancelamento;

    @Setter
    @Column(name = "data_entrega", columnDefinition = "datetime")
    private LocalDateTime dataEntrega;

    @Setter
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id",
            foreignKey = @ForeignKey(name = "fk_pedido_endereco"),
            referencedColumnName = "id")
    private Endereco endereco;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private StatusPedido statusPedido;

    @Setter
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_usuario"),
            referencedColumnName = "id")
    private Usuario usuario;

    @Setter
    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_restaurante"),
            referencedColumnName = "id")
    private Restaurante restaurante;

    @Setter
    @ManyToOne
    @JoinColumn(name = "formas_pagamento_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_formas_pagamento"),
            referencedColumnName = "id")
    private FormasDePagamento formasDePagamento;

    @Setter
    @OneToMany(mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @ToString.Exclude
    private List<ItemPedido> itensPedidos = new ArrayList<>();

    /*##########################################     MÉTODOS      ####################################################*/
    public Pedido() {
        this.calcularValorSubtotal();
        this.calcularValorTotal();
    }

    public double getValorTotalDouble() {
        return this.getValorTotal().doubleValue();
    }

    public void adicionarItem(ItemPedido item) {
        this.getItensPedidos().add(item);
    }

    private void calcularValorSubtotal() {
        double subTotal = this.itensPedidos.stream()
                .mapToDouble(ItemPedido::getValorTotalDouble)
                .sum();
        this.subtotal = BigDecimal.valueOf(subTotal);
    }

    private void calcularValorTotal() {
        if(this.getSubtotal() != null && this.getTaxaFrete() != null) {
            this.valorTotal = getSubtotal().add(getTaxaFrete());
        }
    }
}