package com.dan.esr.core.mapper;

import com.dan.esr.api.v1.models.input.endereco.EnderecoInput;
import com.dan.esr.api.v1.models.input.itempedido.ItemPedidoInput;
import com.dan.esr.api.v1.models.input.pedido.PedidoInput;
import com.dan.esr.api.v1.models.input.produto.FotoProdutoInput;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteProdutosOutput;
import com.dan.esr.api.v2.models.input.CidadeInputV2;
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
                .addMapping(Restaurante::getNomesFormasPagamento, RestauranteOutput::setNomeFormasPagamento);

        //Configurando o mapeamento de Restaurante para RestauranteProdutosOutput
        mapper.createTypeMap(Restaurante.class, RestauranteProdutosOutput.class)
                .addMapping(Restaurante::getProdutos, RestauranteProdutosOutput::setProdutos);

        mapper.createTypeMap(PedidoInput.class, Pedido.class)
                .addMappings(mapping -> mapping.skip(Pedido::setId));

        mapper.createTypeMap(EnderecoInput.class, Endereco.class)
                .addMappings(mapping -> mapping.skip(Endereco::setId));

        mapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapping -> mapping.skip(ItemPedido::setId));

        mapper.createTypeMap(CidadeInputV2.class, Cidade.class)
                .addMappings(mapping -> mapping.skip(Cidade::setId));

        mapper.createTypeMap(FotoProdutoInput.class, FotoProduto.class)
                .addMappings(mapping -> {
                    mapping.map(FotoProdutoInput::getNomeArquivo, FotoProduto::setNomeArquivo);
                    mapping.map(FotoProdutoInput::getContentType, FotoProduto::setContentType);
                    mapping.map(FotoProdutoInput::getTamanho, FotoProduto::setTamanho);
                });

        return mapper;
    }
}