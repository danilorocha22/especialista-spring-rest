package com.dan.esr.api.models.output.pedido;

import com.dan.esr.api.models.output.endereco.EnderecoOutput;
import com.dan.esr.api.models.output.itempedido.ItemPedidoOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteIdNomeOutput;
import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import com.dan.esr.api.models.output.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
@ApiModel(value = "Pedido")
@Relation(collectionRelation = "pedidos")
@EqualsAndHashCode(of = "codigo", callSuper = false)
public class PedidoOutput extends RepresentationModel<PedidoOutput> {

    @JsonView(PedidoView.Resumo.class)
    @ApiModelProperty(example = "3b75fd6e-4a14-4721-8b19-b563c725302e")
    private String codigo;

    @JsonView(PedidoView.Resumo.class)
    @ApiModelProperty(example = "Confirmado")
    private String status;

    @JsonView(PedidoView.Resumo.class)
    @ApiModelProperty(example = "R$ 5,00")
    private BigDecimal taxaFrete;

    @JsonView(PedidoView.Resumo.class)
    @ApiModelProperty(example = "R$ 25,00")
    private BigDecimal subtotal;

    @JsonView(PedidoView.Resumo.class)
    @ApiModelProperty(example = "R$ 35,00")
    private BigDecimal valorTotal;

    @JsonView(PedidoView.Resumo.class)
    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(example = "2019-12-01T20:35:10Z")
    private OffsetDateTime dataConfirmacao;

    @ApiModelProperty(example = "2019-12-01T20:55:30Z")
    private OffsetDateTime dataEntrega;

    @ApiModelProperty(example = "2019-12-01T20:35:00Z")
    private OffsetDateTime dataCancelamento;

    @JsonProperty("restaurante")
    @JsonView(PedidoView.Resumo.class)
    private RestauranteIdNomeOutput restaurante;

    @JsonProperty("formaPagamento")
    @JsonView(PedidoView.Resumo.class)
    @ApiModelProperty(example = "Cartão de Crédito")
    private String formaPagamentoNome;

    @JsonProperty("cliente")
    @JsonView(PedidoView.Resumo.class)
    private UsuarioOutput usuario;

    /*@JsonProperty("cliente")
    private String nomeUsuario;*/

    @JsonProperty("enderecoEntrega")
    private EnderecoOutput endereco;

    @JsonProperty("itens")
    private Set<ItemPedidoOutput> itensPedido;


}