package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.itempedido.ItemPedidoInput;
import com.dan.esr.domain.entities.ItemPedido;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemPedidoAssembler {
    private final ModelMapper mapper;

    public ItemPedido toDomain(ItemPedidoInput itemPedidoInput) {
        return this.mapper.map(itemPedidoInput, ItemPedido.class);
    }

    public List<ItemPedido> toCollectionDomain(List<ItemPedidoInput> itemPedidoInputs) {
        return itemPedidoInputs.stream()
                .map(this::toDomain)
                .toList();
    }
}