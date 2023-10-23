package com.dan.esr.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "restaurantes")
public class Restaurante implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    //@JsonIgnore //ignora o objeto na serialização do json
    //@JsonIgnoreProperties("hibernateLazyInitializer") //ignora o atributo na serialização do json
    @ManyToOne//(fetch = FetchType.LAZY) //para evitar vários selects foi criado uma consulta jpql com join em cozinha
    @JoinColumn(name = "cozinha_id", nullable = false, foreignKey =
    @ForeignKey(name = "fk_restaurante_cozinha"), referencedColumnName = "id")
    @ToString.Exclude
    private Cozinha cozinha;

    @JsonIgnore
    @Embedded
    private Endereco endereco;

    @JsonIgnore
    @CreationTimestamp //Instancia a data uma única vez, na primeira vez de salvar no banco
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataCadastro;

    @JsonIgnore
    @UpdateTimestamp //Instancia uma nova data, sempre que for atualizado
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataAtualizacao;

    //@JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "restaurantes_formas_de_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id", foreignKey =
            @ForeignKey(name = "fk_restaurante_formas_pagamento_restaurante"), referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "formas_de_pagamento_id", foreignKey =
            @ForeignKey(name = "fk_restaurante_formas_pagamento_formas_pagamento"), referencedColumnName = "id"))
    @ToString.Exclude
    private List<FormasDePagamento> formasDePagamento = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
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
