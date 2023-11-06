package com.dan.esr.domain.entities;

import com.dan.esr.core.validation.Groups.CozinhaId;
import com.dan.esr.core.validation.TaxaFrete;
import com.dan.esr.core.validation.ValorZeroIncluiDescricao;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@JsonInclude(NON_NULL)
//@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ValorZeroIncluiDescricao(
        valorField = "taxaFrete",
        descricaoField = "nome",
        descricaoObrigatoria = "Frete Grátis")
@Entity
@Table(name = "restaurantes")
public class Restaurante implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    //@PositiveOrZero//(message = "{TaxaFrete.invalida}")
    //@Multiplo(numero = 5)
    @NotNull
    @TaxaFrete
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    //@JsonIgnore //ignora o objeto na serialização do json
    //@JsonIgnoreProperties("hibernateLazyInitializer") //ignora o atributo na serialização do json
    //@JsonIgnoreProperties(value = "tipo", allowGetters = true)// na desserialização do Restaurante (json -> objeto), o nome da cozinha é ignorado
    //@ToString.Exclude
    @Valid //Valida em cascata as propriedades da cozinha
    @ConvertGroup(to = CozinhaId.class)
    @NotNull
    @ManyToOne//(fetch = FetchType.LAZY) //para evitar vários selects foi criado uma consulta jpql com join em cozinha
    @JoinColumn(name = "cozinha_id", nullable = false, foreignKey =
    @ForeignKey(name = "fk_restaurante_cozinha"), referencedColumnName = "id")
    private Cozinha cozinha;

    //@JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", foreignKey =
    @ForeignKey(name = "fk_restaurante_endereco"), referencedColumnName = "id")
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
            joinColumns = @JoinColumn(name = "restaurante_id", foreignKey =
            @ForeignKey(name = "fk_restaurante_formas_pagamento_restaurante"), referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "formas_de_pagamento_id", foreignKey =
            @ForeignKey(name = "fk_restaurante_formas_pagamento_formas_pagamento"), referencedColumnName = "id"))
    private List<FormasDePagamento> formasDePagamento = new ArrayList<>();

    //@JsonIgnore
    //@ToString.Exclude
    @OneToMany(mappedBy = "restaurante", fetch = FetchType.LAZY)
    private List<Produto> produtos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurante that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
