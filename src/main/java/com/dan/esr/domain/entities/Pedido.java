package com.dan.esr.domain.entities;

import com.dan.esr.domain.entities.enums.StatusPedido;
import com.dan.esr.domain.events.PedidoCanceladoEvent;
import com.dan.esr.domain.events.PedidoConfirmadoEvent;
import com.dan.esr.domain.events.PedidoEmitidoEvent;
import com.dan.esr.domain.exceptions.NegocioException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.dan.esr.domain.entities.enums.StatusPedido.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "pedidos", schema = "dan_food")
public class Pedido extends AbstractAggregateRoot<Pedido> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @ToString.Include
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String codigo;

    @Column(name = "sub_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "taxa_frete", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxaFrete;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(name = "data_criacao", columnDefinition = "datetime", nullable = false)
    private OffsetDateTime dataCriacao;

    @Column(name = "data_confirmacao", columnDefinition = "datetime")
    private OffsetDateTime dataConfirmacao;

    @Column(name = "data_cancelamento", columnDefinition = "datetime")
    private OffsetDateTime dataCancelamento;

    @Column(name = "data_entrega", columnDefinition = "datetime")
    private OffsetDateTime dataEntrega;

    //@JsonIgnore
    @ToString.Include
    @OneToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "endereco_id",
            foreignKey = @ForeignKey(name = "fk_pedido_endereco"),
            referencedColumnName = "id")
    private Endereco endereco;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false, length = 10)
    private StatusPedido status = CRIADO;

    @ToString.Include
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "usuario_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_usuario"),
            referencedColumnName = "id")
    private Usuario usuario;

    @ToString.Include
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_restaurante"),
            referencedColumnName = "id")
    private Restaurante restaurante;

    @ToString.Include
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "formas_pagamento_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_formas_pagamento"),
            referencedColumnName = "id")
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido",cascade = ALL)
    private Set<ItemPedido> itensPedido = new HashSet<>();

    /*########################################     MÉTODOS     ########################################*/
    public void calcularTaxaFrete() {
        if (this.isTaxaFreteValida()) {
            this.setTaxaFrete(this.getTaxaFreteRestaurante());
        } else {
            this.setTaxaFrete(BigDecimal.ZERO);
        }
    }

    public void calcularSubtotal() {
        double subTotal = this.itensPedido.stream()
                .mapToDouble(ItemPedido::getValorTotalDouble)
                .sum();
        this.setSubtotal(BigDecimal.valueOf(subTotal));
    }

    public void calcularTotal() {
        if (nonNull(this.getSubtotal()) && nonNull(this.getTaxaFrete())) {
            BigDecimal total = this.getSubtotal().add(getTaxaFrete());
            this.setValorTotal(total);
        }
    }

    public void confirmar() {
        this.setStatus(CONFIRMADO);
        this.setDataConfirmacao(OffsetDateTime.now());
        this.registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void entregar() {
        this.setStatus(ENTREGUE);
        this.setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        this.setStatus(CANCELADO);
        this.setDataCancelamento(OffsetDateTime.now());
        this.registerEvent(new PedidoCanceladoEvent(this));
    }

    public boolean podeSerConfirmado() {
        return this.getStatus().isStatusPermitido(CONFIRMADO);
    }

    public boolean podeSerEntregue() {
        return this.getStatus().isStatusPermitido(ENTREGUE);
    }

    public boolean podeSerCancelado() {
        return this.getStatus().isStatusPermitido(CANCELADO);
    }

    public String nomeCliente() {
        return this.usuario.getNome();
    }

    public String nomeRestaurante() {
        return this.restaurante.getNome();
    }

    public String nomeFormaPagamento() {
        return this.formaPagamento.getNome();
    }

    public List<ItemPedido> getItensPedido() {
        return this.itensPedido.stream().toList();
    }

    private void setStatus(StatusPedido novoStatus) {
        if (this.getStatus().isStatusNaoPermitido(novoStatus)) {
            throw new NegocioException("O status do pedido nº %s não pode ser alterado de %s para %s."
                    .formatted(getCodigo(), getStatus().name(), novoStatus.name()));
        }
        this.status = novoStatus;
    }

    @PrePersist
    private void gerarCodigoPedido() {
        this.setCodigo(UUID.randomUUID().toString());
        this.registerEvent(new PedidoEmitidoEvent(this));
    }

    private boolean isTaxaFreteValida() {
        return !(isNull(this.getRestaurante()) || isNull(this.getTaxaFreteRestaurante()));
    }

    private BigDecimal getTaxaFreteRestaurante() {
        return this.getRestaurante().getTaxaFrete();
    }

    private void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    private void setSubtotal(BigDecimal valor) {
        this.subtotal = valor;
    }

    private void setTaxaFrete(BigDecimal valor) {
        this.taxaFrete = valor;
    }

    private void setValorTotal(BigDecimal valor) {
        this.valorTotal = valor;
    }
}