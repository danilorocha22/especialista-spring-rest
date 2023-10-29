create table itens_pedido
(
    id          bigint         not null auto_increment,
    preco_total decimal(10, 2) not null,
    preco_unit  decimal(10, 2) not null,
    quantidade  smallint       not null,
    observacao  varchar(100),

    pedido_id   bigint         not null,
    produto_id  bigint         not null,

    primary key (id)
) engine = InnoDB;

create table pedidos
(
    id                  bigint                                              not null auto_increment,
    sub_total           decimal(10, 2)                                      not null,
    taxa_frete          decimal(10, 2)                                      not null,
    valor_total         decimal(10, 2)                                      not null,
    data_cancelamento   datetime,
    data_confirmacao    datetime,
    data_criacao        datetime                                            not null,
    data_entrega        datetime,

    endereco_id         bigint,
    formas_pagamento_id bigint                                              not null,
    restaurante_id      bigint                                              not null,
    usuario_id          bigint                                              not null,
    status              enum ('CANCELADO','CONFIRMADO','CRIADO','ENTREGUE') not null,

    primary key (id)
) engine = InnoDB;

alter table itens_pedido
    add constraint uk_item_pedido_produto unique (pedido_id, produto_id);
alter table itens_pedido
    add constraint fk_item_pedido_pedido foreign key (pedido_id) references pedidos (id);
alter table itens_pedido
    add constraint fk_item_pedido_produto foreign key (produto_id) references produtos (id);

alter table pedidos
    add constraint fk_pedido_endereco foreign key (endereco_id) references enderecos (id);
alter table pedidos
    add constraint fk_pedido_formas_pagamento foreign key (formas_pagamento_id) references formas_de_pagamento (id);
alter table pedidos
    add constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurantes (id);
alter table pedidos
    add constraint fk_pedido_usuario foreign key (usuario_id) references usuarios (id);
