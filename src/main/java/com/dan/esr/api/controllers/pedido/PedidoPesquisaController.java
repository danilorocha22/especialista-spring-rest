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
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.dan.esr.core.util.ConfiguracaoCamposPaginacao.novaPaginacaoCamposConfigurados;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoPesquisaController {
    private final PedidoPesquisaService pedidoPesquisaService;
    private final PedidoAssembler pedidoAssembler;

    @GetMapping("/{codigoPedido}")
    public PedidoOutput pedido(@PathVariable String codigoPedido) {
        return this.pedidoAssembler.toModel(
                this.pedidoPesquisaService.buscarPor(codigoPedido)
        );
    }

    @GetMapping
    @JsonView(PedidoView.Resumo.class)
    public Page<PedidoOutput> pesquisaComplexa(
            PedidoFiltro filtro,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        pageable = novaPaginacaoCamposConfigurados(pageable, camposMapeados());
        Page<Pedido> pedidoPage = this.pedidoPesquisaService.filtrarPor(filtro, pageable);
        List<PedidoOutput> pedidosOutput = this.pedidoAssembler.toCollectionModel(pedidoPage.getContent());
        return new PageImpl<>(pedidosOutput, pageable, pedidoPage.getTotalElements());
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

    private static Map<String, String> camposMapeados() {
        return Map.of(
                "codigo", "codigo",
                "codigoPedido", "codigo",
                "nomeProduto", "produto.nome",
                "produtoNome", "produto.nome",
                "restaurante.nome", "restaurante.nome",
                "restauranteNome", "restaurante.nome",
                "nomeRestaurante", "restaurante.nome",
                "nomeCliente", "usuario.nome",
                "cliente.nome", "usuario.nome",
                "nomeUsuario", "usuario.nome"
        );
    }
}