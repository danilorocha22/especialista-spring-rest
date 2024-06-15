package com.dan.esr.domain.entities;

import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.formapagamento.FormaPagamentoNaoEncontradoException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dan.esr.core.util.FormatacaoNomeClasse.formatarNomeClasse;

//@JsonInclude(NON_NULL)
//@ToString
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"formasPagamento"})
@Table(name = "restaurantes", schema = "dan_food")
public class Restaurante implements Serializable, IdentificavelParaAdicionarOuRemover {
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
    @JoinTable(name = "restaurantes_formas_de_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id",
                    foreignKey = @ForeignKey(name = "fk_restaurante_formas_pagamento_restaurante"),
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "formas_de_pagamento_id",
                    foreignKey = @ForeignKey(name = "fk_restaurante_formas_pagamento_formas_pagamento"),
                    referencedColumnName = "id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurantes_usuarios_responsaveis",
            joinColumns = @JoinColumn(name = "restaurante_id",
                    foreignKey = @ForeignKey(name = "fk_restaurantes_usuarios_restaurante"),
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id",
                    foreignKey = @ForeignKey(name = "fk_restaurantes_usuarios_usuario"),
                    referencedColumnName = "id"))
    private Set<Usuario> usuariosResponsaveis = new HashSet<>();

    //@JsonIgnore
    //@ToString.Exclude
    @OneToMany(mappedBy = "restaurante",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Produto> produtos = new HashSet<>();

    //@ColumnDefault("true")
    @Column(nullable = false)
    private boolean ativo = true;

    @Column(nullable = false)
    private boolean aberto = true;

    /*###################################   MÉTODOS   ###########################################*/

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

    private void setAtivo(boolean ativar) {
        this.ativo = ativar;
    }

    public boolean isNovo() {
        return getId() == null;
    }

    public void abrir() {
        validarRestaurantePodeAbrir();
        setAberto(true);
    }

    private void setAberto(boolean abrir) {
        this.aberto = abrir;
    }

    private void validarRestaurantePodeAbrir() {
        if (isAberto()) {
            throw new NegocioException("O restaurante não pode ser aberto novamente.");
        }

        if (isInativo()) {
            throw new NegocioException("O restaurante não pode ser aberto enquanto estiver inativo.");
        }
    }

    private boolean isInativo() {
        return !isAtivo();
    }

    public void fechar() {
        if (isFechado()) {
            throw new NegocioException("O Restaurante não pode ser fechado novamente.");
        }
        setAberto(false);
    }

    private boolean isFechado() {
        return !isAberto();
    }

    public List<String> getFormasDePagamento() {
        return formasPagamento
                .stream()
                .map(FormaPagamento::getNome)
                .toList();
    }

    public <T extends IdentificavelParaAdicionarOuRemover> void adicionar(T entidade) {
        validarSePossui(entidade, true);
        boolean naoAdicionado = !adicionado(entidade);
        validarAdicionadoOuRemovido(naoAdicionado, entidade, "adicionado(a)");
    }

    public <T extends IdentificavelParaAdicionarOuRemover> void adicionarTodos(List<T> entidades) {
        entidades.forEach(this::adicionar);
    }

    public <T extends IdentificavelParaAdicionarOuRemover> void remover(T entidade) {
        validarSePossui(entidade, false);
        boolean naoRemovido = !removido(entidade);
        validarAdicionadoOuRemovido(naoRemovido, entidade, "removido(a)");
    }

    private <T extends IdentificavelParaAdicionarOuRemover> boolean adicionado(T entidade) {
        boolean adicionado = false;
        if (entidade instanceof Produto) {
            adicionado = this.produtos.add((Produto) entidade);
        } else if (entidade instanceof FormaPagamento) {
            adicionado = this.formasPagamento.add((FormaPagamento) entidade);
        } else if (entidade instanceof Usuario) {
            adicionado = this.usuariosResponsaveis.add((Usuario) entidade);
        }
        return adicionado;
    }

    private <T extends IdentificavelParaAdicionarOuRemover> boolean removido(T entidade) {
        boolean removido = false;
        if (entidade instanceof Produto) {
            removido = this.produtos.removeIf(p -> p.getId().equals(entidade.getId()));
        } else if (entidade instanceof FormaPagamento) {
            removido = this.formasPagamento.removeIf(p -> p.getId().equals(entidade.getId()));
        } else if (entidade instanceof Usuario) {
            removido = this.usuariosResponsaveis.removeIf(p -> p.getId().equals(entidade.getId()));
        }
        return removido;
    }

    private <T extends IdentificavelParaAdicionarOuRemover> void validarSePossui(T entidade, boolean possui) {
        boolean contem = isContem(entidade);
        if (possui && contem) {
            throw new NegocioException(mensagemDeErro(entidade, "já existe"));
        } else if (!possui && !contem) {
            throw new FormaPagamentoNaoEncontradoException(mensagemDeErro(entidade, "não existe"));
        }
    }

    private <T extends IdentificavelParaAdicionarOuRemover> boolean isContem(T entidade) {
        boolean contem = false;
        if (entidade instanceof FormaPagamento) {
            contem = this.formasPagamento.contains(entidade);
        } else if (entidade instanceof Usuario) {
            contem = this.usuariosResponsaveis.contains(entidade);
        }
        return contem;
    }

    private <T extends IdentificavelParaAdicionarOuRemover> void validarAdicionadoOuRemovido(
            boolean condicao,
            T entidade,
            String msg
    ) {
        if (condicao) {
            throw new NegocioException(mensagemDeErro(entidade, "não foi ".concat(msg)));
        }
    }

    /**
     * @param <T>      o objeto deve ser subtipo de IdentificavelParaAdicionarOuRemover
     * @param entidade é o objeto (instância).
     * @param acao     é o que tentou-se realizar e falhou. Ex: adicionado; removido, etc.
     * @return mensagem de erro formatada
     */
    private <T extends IdentificavelParaAdicionarOuRemover> String mensagemDeErro(T entidade, String acao) {
        String nome = entidade.getClass().getSimpleName();
        return ("%s %s (id %s) %s no restaurante %s (id %s), verifique os dados informados e tente " +
                "novamente. Se o problema persistir, contate o administrador.")
                .formatted(formatarNomeClasse(nome), entidade.getNome(),
                        entidade.getId(), acao, getNome(), getId());
    }

    public void validarFormaPagamento(FormaPagamento formaPagamento) {
        this.formasPagamento.stream()
                .filter(fp -> fp.equals(formaPagamento))
                .findFirst()
                .orElseThrow(() -> new NegocioException(("A forma de pagamento %s com ID %s não está disponível no " +
                        "restaurante com ID %s. Formas de Pagamento disponíveis: %s")
                        .formatted(formaPagamento.getNome(), formaPagamento.getId(), id,
                                this.getFormasPagamento().stream().toList())));

    }

    public void validarSeAberto() {
        if (this.isFechado()) {
            throw new NegocioException(("O restaurante com ID %s está fechado e não receber seu pedido" +
                    "no momento. Tente novamente mais tarde, obrigado!")
                    .formatted(id));
        }
    }

    public void validarResponsaveis() {
        if (getUsuariosResponsaveis().isEmpty()) {
            throw new NegocioException("O restaurante %s não possui responsável cadastrado no momento."
                    .formatted(getNome()));
        }
    }
}