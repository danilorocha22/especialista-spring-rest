package com.dan.esr.api.controllers.pedido;

import com.dan.esr.api.models.output.pedido.PedidoOutput;
import com.dan.esr.api.models.output.pedido.PedidoResumoOutput;
import com.dan.esr.api.models.output.view.PedidoView;
import com.dan.esr.core.assemblers.PedidoAssembler;
import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.repositories.filter.PedidoFiltro;
import com.dan.esr.domain.services.pedido.PedidoPesquisaService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoPesquisaController {
    private final PedidoPesquisaService pedidoPesquisaService;
    private final PedidoAssembler pedidoAssembler;

    @GetMapping("/{codigoPedido}")
    public PedidoOutput pedido(@PathVariable String codigoPedido) {
        Pedido pedido = this.pedidoPesquisaService.buscarPor(codigoPedido);
        return this.pedidoAssembler.toModel(pedido);
    }

    @GetMapping
    @JsonView(PedidoView.Resumo.class)
    public List<PedidoOutput> pesquisaComplexa(PedidoFiltro filtro) {
        List<Pedido> pedidos = this.pedidoPesquisaService.filtrarPor(filtro);
        return this.pedidoAssembler.toCollection(pedidos);
    }

    @GetMapping("/filtrados")
    public MappingJacksonValue todos(@RequestParam(required = false) String campos) {
        List<Pedido> pedidos = this.pedidoPesquisaService.todos();
        List<PedidoResumoOutput> pedidosModel = this.pedidoAssembler.toCollectionResumo(pedidos);

        MappingJacksonValue pedidoMapping = new MappingJacksonValue(pedidosModel);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        String filtro = "pedidoFilter";
        filterProvider.addFilter(filtro, SimpleBeanPropertyFilter.serializeAll());
        pedidoMapping.setFilters(filterProvider);

        if (StringUtils.hasText(campos)) {
            filterProvider.addFilter(filtro, SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
        }

        return pedidoMapping;
    }
}