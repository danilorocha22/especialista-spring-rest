package com.dan.esr.domain.entities;

import com.dan.esr.domain.exceptions.NegocioException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@JsonInclude(NON_NULL)
//@ToString
@Getter
@Setter
@ToString(exclude = {"formasPagamento"})
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "restaurantes", schema = "dan_food")
public class Restaurante implements Serializable, Comparable<Restaurante> {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank
    @Column(nullable = false)
    private String nome;

    //@PositiveOrZero//(message = "{TaxaFrete.invalida}")
    //@Multiplo(numero = 5)
    //@NotNull
    //@TaxaFrete
    @Column(name = "taxa_frete", columnDefinition = "DECIMAL(10, 2)", nullable = false)
    @ColumnDefault("0.00")
    private BigDecimal taxaFrete;

    //@JsonIgnore //ignora o objeto na serialização do json
    //@JsonIgnoreProperties("hibernateLazyInitializer") //ignora o atributo na serialização do json
    //@JsonIgnoreProperties(value = "tipo", allowGetters = true)// na desserialização do Restaurante (json -> objeto), o nome da cozinha é ignorado
    //@ToString.Exclude
    //@Valid //Valida em cascata as propriedades da cozinha
    //@ConvertGroup(to = CozinhaId.class)
    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY) //para evitar vários selects foi criado uma consulta jpql com join em cozinha
    @JoinColumn(name = "cozinha_id", nullable = false, foreignKey =
    @ForeignKey(name = "fk_restaurante_cozinha"), referencedColumnName = "id")
    private Cozinha cozinha;

    //@JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "endereco_id",
            foreignKey = @ForeignKey(name = "fk_restaurante_endereco"),
            referencedColumnName = "id")
    private Endereco endereco;

    //@JsonIgnore
    @CreationTimestamp //Instancia a data uma única vez, na primeira vez de salvar no banco
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    //@JsonIgnore
    @UpdateTimestamp //Instancia uma nova data, sempre que for atualizado
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    //@JsonIgnore
    //@ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "restaurantes_formas_de_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id",
                    foreignKey = @ForeignKey(name = "fk_restaurante_formas_pagamento_restaurante"),
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "formas_de_pagamento_id",
                    foreignKey = @ForeignKey(name = "fk_restaurante_formas_pagamento_formas_pagamento"),
                    referencedColumnName = "id"))
    private Set<FormasPagamento> formasPagamento = new HashSet<>();

    //@JsonIgnore
    //@ToString.Exclude
    @OneToMany(mappedBy = "restaurante",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Produto> produtos = new ArrayList<>();

    @Column(nullable = false)
    //@ColumnDefault("true")
    private boolean ativo = true;

    public Restaurante() {
        if (taxaFrete == null) {
            taxaFrete = BigDecimal.ZERO;
        }
    }

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public boolean isNovo() {
        return getId() == null;
    }

    public List<String> getFormasDePagamento() {
        return formasPagamento
                .stream()
                .map(FormasPagamento::getDescricao)
                .toList();
    }

    @Override
    public int compareTo(Restaurante restaurante) {
        return this.getId().compareTo(restaurante.getId());
    }

    public void adicionarFormaPagamento(FormasPagamento formasPagamento) {
        if (isExiste(formasPagamento)) {
            throw new NegocioException("Não é possível adicionar novamente a forma de pagamento, " +
                    "pois ela já existe no restaurante.");
        }
        boolean adicionado = this.formasPagamento.add(formasPagamento);
        validarSeFormaPagamentoFoiAdicionado(adicionado);
    }

    public void retirarFormaPagamento(FormasPagamento formasPagamento) {
        if (isNaoExiste(formasPagamento)) {
            throw new NegocioException("Não é possível remover a forma de pagamento, " +
                    "pois ela não existe no restaurante.");
        }
        boolean removido = this.formasPagamento.remove(formasPagamento);
        validarSeFormaPagamentoFoiRemovido(removido);
    }

    private void validarSeFormaPagamentoFoiAdicionado(boolean adicionado) {
        if (!adicionado) {
            throw new NegocioException("Forma de pagamento não foi adicionada, verifique os dados " +
                    "informados e tente novamente. Se o problema persistir, contate o administrador.");
        }
    }

    private void validarSeFormaPagamentoFoiRemovido(boolean removido) {
        if (!removido) {
            throw new NegocioException("Forma de pagamento não foi removida, verifique os dados " +
                    "informados e tente novamente. Se o problema persistir, contate o administrador.");
        }
    }

    private boolean isExiste(FormasPagamento formasPagamento) {
        return this.formasPagamento.contains(formasPagamento);
    }

    private boolean isNaoExiste(FormasPagamento formasPagamento) {
        return !isExiste(formasPagamento);
    }
}