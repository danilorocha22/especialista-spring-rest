package com.dan.esr.api.v1.controllers.pedido;

import com.dan.esr.api.v1.assemblers.PedidoAssembler;
import com.dan.esr.api.v1.assemblers.PedidoResumoAssembler;
import com.dan.esr.api.v1.models.output.pedido.PedidoOutput;
import com.dan.esr.api.v1.models.output.pedido.PedidoResumoOutput;
import com.dan.esr.api.v1.openapi.documentation.pedido.PedidoPesquisaDocumentation;
import com.dan.esr.core.helper.PageWrapperHelper;
import com.dan.esr.core.helper.PageableWrapperHelper;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.filter.PedidoFiltro;
import com.dan.esr.domain.services.pedido.PedidoPesquisaService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/pedidos", produces = APPLICATION_JSON_VALUE)
public class PedidoPesquisaController implements PedidoPesquisaDocumentation {
    private final PedidoPesquisaService pedidoPesquisaService;
    private final PedidoAssembler pedidoAssembler;
    private final PedidoResumoAssembler pedidoResumoAssembler;
    private final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @Override
    @CheckSecurity.Pedidos.PodeBuscar
    @GetMapping("/{codigoPedido}")
    public PedidoOutput pedido(@PathVariable String codigoPedido) {
        return this.pedidoAssembler.toModel(
                this.pedidoPesquisaService.buscarPor(codigoPedido)
        );
    }

    @Override
    @GetMapping
    @CheckSecurity.Pedidos.PodePesquisar
    public ResponseEntity<?> pesquisaComplexa(
            PedidoFiltro filtro,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        try {
            Pageable pageableConfigurado = PageableWrapperHelper.of(pageable, getCampos());
            Page<Pedido> pedidosPage = this.pedidoPesquisaService.filtrarPor(filtro, pageableConfigurado);
            pedidosPage = PageWrapperHelper.of(pedidosPage, pageable);

            if (pedidosPage.isEmpty()) {
                return ResponseEntity.ok("Nenhum pedido encontrado.");
            }

            return ResponseEntity.ok(
                    this.pagedResourcesAssembler.toModel(pedidosPage, this.pedidoAssembler)
            );
        } catch (Exception ex) {
            log.error("Erro ao pesquisar pedidos: {}", ex.getMessage(), ex);
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    @GetMapping("/filtrados")
    public ResponseEntity<?> pedidos(@RequestParam(required = false) String campos) {
        List<Pedido> pedidos = this.pedidoPesquisaService.todos();
        CollectionModel<PedidoResumoOutput> pedidosModel = this.pedidoResumoAssembler.toCollectionModel(pedidos);

        if (pedidosModel.getContent().isEmpty()) {
            return ResponseEntity.ok("Nenhum pedido encontrado.");
        }

        MappingJacksonValue pedidoMapping = new MappingJacksonValue(pedidosModel);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        String filtro = "pedidoFilter";

        filterProvider.addFilter(filtro, SimpleBeanPropertyFilter.serializeAll());
        pedidoMapping.setFilters(filterProvider);

        if (StringUtils.hasText(campos)) {
            filterProvider.addFilter(filtro, SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
        }

        return ResponseEntity.ok(pedidoMapping);
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