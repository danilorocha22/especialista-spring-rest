package com.dan.esr.domain.entities;

import com.dan.esr.domain.entities.enums.StatusPedido;
import com.dan.esr.domain.events.PedidoCanceladoEvent;
import com.dan.esr.domain.events.PedidoConfirmadoEvent;
import com.dan.esr.domain.events.PedidoEmitidoEvent;
import com.dan.esr.domain.exceptions.NegocioException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.dan.esr.domain.entities.enums.StatusPedido.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id",
            foreignKey = @ForeignKey(name = "fk_pedido_endereco"),
            referencedColumnName = "id")
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private StatusPedido status = CRIADO;

    @ToString.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_usuario"),
            referencedColumnName = "id")
    private Usuario usuario;

    @ToString.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_restaurante"),
            referencedColumnName = "id")
    private Restaurante restaurante;

    @ToString.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formas_pagamento_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_formas_pagamento"),
            referencedColumnName = "id")
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido",
            cascade = CascadeType.ALL)
    private Set<ItemPedido> itensPedido = new HashSet<>();

    /*########################################     MÉTODOS     ########################################*/
    public void calcularTaxaFrete() {
        if (isTaxaFreteValida()) {
            setTaxaFrete(this.restaurante.getTaxaFrete());
        } else {
            setTaxaFrete(BigDecimal.ZERO);
        }
    }

    public void calcularSubtotal() {
        double subTotal = this.itensPedido.stream()
                .mapToDouble(ItemPedido::getValorTotalDouble)
                .sum();
        setSubtotal(BigDecimal.valueOf(subTotal));
    }

    public void calcularTotal() {
        if (this.getSubtotal() != null && this.getTaxaFrete() != null) {
            BigDecimal total = getSubtotal().add(getTaxaFrete());
            setValorTotal(total);
        }
    }

    public void confirmar() {
        setStatus(CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void entregar() {
        setStatus(ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
        registerEvent(new PedidoCanceladoEvent(this));
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

    private void setStatus(StatusPedido novoStatus) {
        if (getStatus().isStatusNaoPermitido(novoStatus)) {
            throw new NegocioException("O status do pedido nº %s não pode ser alterado de %s para %s."
                    .formatted(getCodigo(), getStatus().name(), novoStatus.name()));
        }
        this.status = novoStatus;
    }

    @PrePersist
    private void gerarCodigoPedido() {
        setCodigo(UUID.randomUUID().toString());
        registerEvent(new PedidoEmitidoEvent(this));
    }

    private boolean isTaxaFreteValida() {
        return !(Objects.isNull(this.restaurante) || this.restaurante.getTaxaFrete() == null);
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