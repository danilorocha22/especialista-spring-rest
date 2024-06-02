package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.pedido.PedidoInput;
import com.dan.esr.api.models.output.pedido.PedidoOutput;
import com.dan.esr.api.models.output.pedido.PedidoResumoOutput;
import com.dan.esr.api.models.output.pedido.PedidoStatusOutput;
import com.dan.esr.domain.entities.Pedido;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PedidoAssembler {
    private final ModelMapper mapper;

    public PedidoOutput toModel(Pedido pedido) {
        return this.mapper.map(pedido, PedidoOutput.class);
    }

    public PedidoStatusOutput toModelStatus(Pedido pedido) {
        return this.mapper.map(pedido, PedidoStatusOutput.class);
    }

    public PedidoResumoOutput toModelResumo(Pedido pedido) {
        return this.mapper.map(pedido, PedidoResumoOutput.class
        );
    }

    public List<PedidoOutput> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toModel)
                .toList();
    }

    public List<PedidoResumoOutput> toCollectionResumo(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toModelResumo)
                .toList();
    }

    public Pedido toDomain(PedidoInput pedidoInput) {
        return this.mapper.map(pedidoInput, Pedido.class);
    }
}