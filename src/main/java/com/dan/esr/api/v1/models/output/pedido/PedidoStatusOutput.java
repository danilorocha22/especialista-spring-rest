package com.dan.esr.api.v1.models.output.pedido;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class PedidoStatusOutput extends RepresentationModel<PedidoStatusOutput> {
    @ApiModelProperty(example = "1")
    private String codigo;

    @ApiModelProperty("CRIADO")
    private String status;
}