package com.dan.esr.api.models.output.formapagamento;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation("formasPagamento")
@EqualsAndHashCode(of = "id", callSuper = false)
public class FormaPagamentoOutput extends RepresentationModel<FormaPagamentoOutput> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Débito")
    private String nome;
}