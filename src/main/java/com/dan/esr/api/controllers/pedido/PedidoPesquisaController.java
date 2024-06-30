package com.dan.esr.api.controllers.pedido;

import com.dan.esr.api.models.output.pedido.PedidoOutput;
import com.dan.esr.api.models.output.pedido.PedidoResumoOutput;
import com.dan.esr.api.models.output.view.PedidoView;
import com.dan.esr.api.openapi.documentation.pedido.PedidoPesquisaDocumentation;
import com.dan.esr.core.assemblers.PedidoAssembler;
import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.filter.PedidoFiltro;
import com.dan.esr.domain.services.pedido.PedidoPesquisaService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.dan.esr.core.util.PaginacaoCamposHelper.novaPaginacaoCamposConfigurados;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pedidos", produces = APPLICATION_JSON_VALUE)
public class PedidoPesquisaController implements PedidoPesquisaDocumentation {
    private final PedidoPesquisaService pedidoPesquisaService;
    private final PedidoAssembler pedidoAssembler;

    @Override
    @GetMapping("/{codigoPedido}")
    public PedidoOutput pedido(@PathVariable String codigoPedido) {
        return this.pedidoAssembler.toModel(
                this.pedidoPesquisaService.buscarPor(codigoPedido)
        );
    }

    @Override
    @GetMapping
    @JsonView(PedidoView.Resumo.class)
    public Page<PedidoOutput> pesquisaComplexa(
            PedidoFiltro filtro,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        pageable = novaPaginacaoCamposConfigurados(pageable, getCampos());
        Page<Pedido> pedidoPage = this.pedidoPesquisaService.filtrarPor(filtro, pageable);
        List<PedidoOutput> pedidosOutput = this.pedidoAssembler.toCollectionModel(pedidoPage.getContent());
        return new PageImpl<>(pedidosOutput, pageable, pedidoPage.getTotalElements());
    }

    @Override
    @GetMapping("/filtrados")
    public MappingJacksonValue pedidos(@RequestParam(required = false) String campos) {
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

    private static Map<String, String> getCampos() {
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