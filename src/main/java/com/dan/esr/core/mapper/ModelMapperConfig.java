package com.dan.esr.core.mapper;

import com.dan.esr.api.models.input.itempedido.ItemPedidoInput;
import com.dan.esr.api.models.input.pedido.PedidoInput;
import com.dan.esr.api.models.input.produto.FotoProdutoInput;
import com.dan.esr.api.models.output.cidade.CidadeNomeOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteProdutosOutput;
import com.dan.esr.domain.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        //Configurando o mapeamento de Restaurante para RestauranteFormasDePagamentosOutput
        mapper.createTypeMap(Restaurante.class, RestauranteOutput.class)
                .addMapping(Restaurante::getFormasDePagamento, RestauranteOutput::setDescricaoFormasPagamento);

        //Configurando o mapeamento de Restaurante para RestauranteProdutosOutput
        mapper.createTypeMap(Restaurante.class, RestauranteProdutosOutput.class)
                .addMapping(Restaurante::getProdutos, RestauranteProdutosOutput::setProdutos);

        //Configurando o mapeamento de Cidade para CidadeNomeOutput
        mapper.createTypeMap(Cidade.class, CidadeNomeOutput.class)
                .addMappings(mapping -> mapping.map(Cidade::getCidadeEstado, CidadeNomeOutput::setNome));

        mapper.createTypeMap(PedidoInput.class, Pedido.class)
                .addMappings(mapping -> mapping.skip(Pedido::setId));

        mapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapping -> mapping.skip(ItemPedido::setId));

        mapper.createTypeMap(FotoProdutoInput.class, FotoProduto.class)
                .addMappings(mapping -> {
                    mapping.map(FotoProdutoInput::getNomeArquivo, FotoProduto::setNomeArquivo);
                    mapping.map(FotoProdutoInput::getContentType, FotoProduto::setContentType);
                    mapping.map(FotoProdutoInput::getTamanho, FotoProduto::setTamanho);
                });

        return mapper;
    }
}
