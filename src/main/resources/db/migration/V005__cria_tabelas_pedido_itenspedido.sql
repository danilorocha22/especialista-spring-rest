create table itens_pedido
(
    preco_total decimal(10, 2) not null,
    preco_unit  decimal(10, 2) not null,
    quantidade  integer        not null,
    id          bigint         not null auto_increment,
    pedido_id   bigint         not null,
    produto_id  bigint         not null,
    observacao  varchar(100),
    primary key (id)
) engine = InnoDB;
create table pedidos
(
    sub_total            decimal(10, 2)                                      not null,
    taxa_frete           decimal(10, 2)                                      not null,
    valor_total          decimal(10, 2)                                      not null,
    data_cancelamento    datetime,
    data_confirmacao     datetime,
    data_criacao         datetime                                            not null,
    data_entrega         datetime,
    endereco_cidade_id   bigint                                              not null,
    formas_pagamento_id  bigint                                              not null,
    id                   bigint                                              not null auto_increment,
    restaurante_id       bigint                                              not null,
    usuario_id           bigint                                              not null,
    endereco_cep         varchar(9)                                          not null,
    status               enum ('CANCELADO','CONFIRMADO','CRIADO','ENTREGUE') not null,
    endereco_numero      varchar(20)                                         not null,
    endereco_bairro      varchar(60)                                         not null,
    endereco_complemento varchar(60),
    endereco_logradouro  varchar(100)                                        not null,
    primary key (id)
) engine = InnoDB;


alter table itens_pedido
    add constraint fk_item_pedido_pedido foreign key (pedido_id) references pedidos (id);
alter table itens_pedido
    add constraint fk_item_pedido_produto foreign key (produto_id) references produtos (id);
alter table pedidos
    add constraint fk_endereco_cidade foreign key (endereco_cidade_id) references cidades (id);
alter table pedidos
    add constraint fk_pedido_formas_pagamento foreign key (formas_pagamento_id) references formas_de_pagamento (id);
alter table pedidos
    add constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurantes (id);
alter table pedidos
    add constraint fk_pedido_usuario foreign key (usuario_id) references usuarios (id);
