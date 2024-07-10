package com.dan.esr.api.models.output.formapagamento;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "formasPagamento", itemRelation = "formaPagamento")
public class FormaPagamentoOutput extends RepresentationModel<FormaPagamentoOutput> {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Débito")
    private String nome;
}