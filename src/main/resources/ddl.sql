create table itens_pedido
(
    preco_total decimal(10, 2) not null,
    preco_unit  decimal(10, 2) not null,
    quantidade  smallint       not null,
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
    add constraint uk_item_pedido_produto unique (pedido_id, produto_id);

alter table itens_pedido
    add constraint fk_item_pedido_pedido foreign key (pedido_id) references pedidos (id);
alter table itens_pedido
    add constraint fk_item_pedido_produto foreign key (produto_id) references produtos (id);

alter table pedidos
    add constraint fk_pedido_endereco_cidade foreign key (endereco_cidade_id) references cidades (id);
alter table pedidos
    add constraint fk_pedido_formas_pagamento foreign key (formas_pagamento_id) references formas_de_pagamento (id);
alter table pedidos
    add constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurantes (id);
alter table pedidos
    add constraint fk_pedido_usuario foreign key (usuario_id) references usuarios (id);
create table cidades (estado_id bigint not null, id bigint not null auto_increment, nome varchar(80) not null, primary key (id)) engine=InnoDB;
create table cozinhas (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table estados (id bigint not null auto_increment, nome varchar(80) not null, primary key (id)) engine=InnoDB;
create table formas_de_pagamento (id bigint not null auto_increment, descricao varchar(20) not null, primary key (id)) engine=InnoDB;
create table grupos (id bigint not null auto_increment, nome varchar(20) not null, primary key (id)) engine=InnoDB;
create table grupos_permissoes (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB;
create table itens_pedido (preco_total decimal(10,2) not null, preco_unit decimal(10,2) not null, quantidade smallint not null, id bigint not null auto_increment, pedido_id bigint not null, produto_id bigint not null, observacao varchar(100), primary key (id)) engine=InnoDB;
create table pedidos (sub_total decimal(10,2) not null, taxa_frete decimal(10,2) not null, valor_total decimal(10,2) not null, data_cancelamento datetime, data_confirmacao datetime, data_criacao datetime not null, data_entrega datetime, endereco_cidade_id bigint not null, formas_pagamento_id bigint not null, id bigint not null auto_increment, restaurante_id bigint not null, usuario_id bigint not null, endereco_cep varchar(9) not null, status enum ('CANCELADO','CONFIRMADO','CRIADO','ENTREGUE') not null, endereco_numero varchar(20) not null, endereco_bairro varchar(60) not null, endereco_complemento varchar(60), endereco_logradouro varchar(100) not null, primary key (id)) engine=InnoDB;
create table permissoes (id bigint not null auto_increment, nome varchar(20) not null, descricao varchar(80) not null, primary key (id)) engine=InnoDB;
create table produtos (ativo bit not null, preco decimal(38,2) not null, id bigint not null auto_increment, restaurante_id bigint not null, nome varchar(20) not null, descricao varchar(255) not null, primary key (id)) engine=InnoDB;
create table restaurantes (taxa_frete decimal(38,2) not null, cozinha_id bigint not null, data_atualizacao datetime not null, data_cadastro datetime not null, endereco_cidade_id bigint not null, id bigint not null auto_increment, endereco_cep varchar(9) not null, endereco_numero varchar(20) not null, endereco_bairro varchar(60) not null, endereco_complemento varchar(60), endereco_logradouro varchar(100) not null, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table restaurantes_formas_de_pagamento (formas_de_pagamento_id bigint not null, restaurante_id bigint not null) engine=InnoDB;
create table usuarios (data_cadastro datetime(6) not null, id bigint not null auto_increment, email varchar(255) not null, nome varchar(255) not null, senha varchar(255) not null, primary key (id)) engine=InnoDB;
create table usuarios_grupos (grupo_id bigint not null, usuario_id bigint not null) engine=InnoDB;
alter table itens_pedido add constraint uk_item_pedido_produto unique (pedido_id, produto_id);
alter table itens_pedido add constraint UK_cm300bdp135gqtq6kgw3rwxlf unique (produto_id);
alter table cidades add constraint fk_cidade_estado foreign key (estado_id) references estados (id);
alter table grupos_permissoes add constraint fk_grupo_permissoes_permissoes foreign key (permissao_id) references permissoes (id);
alter table grupos_permissoes add constraint fk_grupo_permissoes_grupo foreign key (grupo_id) references grupos (id);
alter table itens_pedido add constraint fk_item_pedido_pedido foreign key (pedido_id) references pedidos (id);
alter table itens_pedido add constraint fk_item_pedido_produto foreign key (produto_id) references produtos (id);
alter table pedidos add constraint fk_endereco_cidade foreign key (endereco_cidade_id) references cidades (id);
alter table pedidos add constraint fk_pedido_formas_pagamento foreign key (formas_pagamento_id) references formas_de_pagamento (id);
alter table pedidos add constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurantes (id);
alter table pedidos add constraint fk_pedido_usuario foreign key (usuario_id) references usuarios (id);
alter table produtos add constraint fk_produto_restaurante foreign key (restaurante_id) references restaurantes (id);
alter table restaurantes add constraint fk_restaurante_cozinha foreign key (cozinha_id) references cozinhas (id);
alter table restaurantes add constraint fk_endereco_cidade foreign key (endereco_cidade_id) references cidades (id);
alter table restaurantes_formas_de_pagamento add constraint fk_restaurante_formas_pagamento_formas_pagamento foreign key (formas_de_pagamento_id) references formas_de_pagamento (id);
alter table restaurantes_formas_de_pagamento add constraint fk_restaurante_formas_pagamento_restaurante foreign key (restaurante_id) references restaurantes (id);
alter table usuarios_grupos add constraint fk_usuario_grupo_grupo foreign key (grupo_id) references grupos (id);
alter table usuarios_grupos add constraint fk_usuario_grupo_usuario foreign key (usuario_id) references usuarios (id);
create table cidades (estado_id bigint not null, id bigint not null auto_increment, nome varchar(80) not null, primary key (id)) engine=InnoDB;
create table cozinhas (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table estados (id bigint not null auto_increment, nome varchar(80) not null, primary key (id)) engine=InnoDB;
create table formas_de_pagamento (id bigint not null auto_increment, descricao varchar(20) not null, primary key (id)) engine=InnoDB;
create table grupos (id bigint not null auto_increment, nome varchar(20) not null, primary key (id)) engine=InnoDB;
create table grupos_permissoes (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB;
create table itens_pedido (preco_total decimal(10,2) not null, preco_unit decimal(10,2) not null, quantidade smallint not null, id bigint not null auto_increment, pedido_id bigint not null, produto_id bigint not null, observacao varchar(100), primary key (id)) engine=InnoDB;
create table pedidos (sub_total decimal(10,2) not null, taxa_frete decimal(10,2) not null, valor_total decimal(10,2) not null, data_cancelamento datetime, data_confirmacao datetime, data_criacao datetime not null, data_entrega datetime, endereco_cidade_id bigint not null, formas_pagamento_id bigint not null, id bigint not null auto_increment, restaurante_id bigint not null, usuario_id bigint not null, endereco_cep varchar(9) not null, status enum ('CANCELADO','CONFIRMADO','CRIADO','ENTREGUE') not null, endereco_numero varchar(20) not null, endereco_bairro varchar(60) not null, endereco_complemento varchar(60), endereco_logradouro varchar(100) not null, primary key (id)) engine=InnoDB;
create table permissoes (id bigint not null auto_increment, nome varchar(20) not null, descricao varchar(80) not null, primary key (id)) engine=InnoDB;
create table produtos (ativo bit not null, preco decimal(38,2) not null, id bigint not null auto_increment, restaurante_id bigint not null, nome varchar(20) not null, descricao varchar(255) not null, primary key (id)) engine=InnoDB;
create table restaurantes (taxa_frete decimal(38,2) not null, cozinha_id bigint not null, data_atualizacao datetime not null, data_cadastro datetime not null, endereco_cidade_id bigint not null, id bigint not null auto_increment, endereco_cep varchar(9) not null, endereco_numero varchar(20) not null, endereco_bairro varchar(60) not null, endereco_complemento varchar(60), endereco_logradouro varchar(100) not null, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table restaurantes_formas_de_pagamento (formas_de_pagamento_id bigint not null, restaurante_id bigint not null) engine=InnoDB;
create table usuarios (data_cadastro datetime(6) not null, id bigint not null auto_increment, email varchar(255) not null, nome varchar(255) not null, senha varchar(255) not null, primary key (id)) engine=InnoDB;
create table usuarios_grupos (grupo_id bigint not null, usuario_id bigint not null) engine=InnoDB;
alter table itens_pedido add constraint uk_item_pedido_produto unique (pedido_id, produto_id);
alter table itens_pedido add constraint UK_cm300bdp135gqtq6kgw3rwxlf unique (produto_id);
alter table cidades add constraint fk_cidade_estado foreign key (estado_id) references estados (id);
alter table grupos_permissoes add constraint fk_grupo_permissoes_permissoes foreign key (permissao_id) references permissoes (id);
alter table grupos_permissoes add constraint fk_grupo_permissoes_grupo foreign key (grupo_id) references grupos (id);
alter table itens_pedido add constraint fk_item_pedido_pedido foreign key (pedido_id) references pedidos (id);
alter table itens_pedido add constraint fk_item_pedido_produto foreign key (produto_id) references produtos (id);
alter table pedidos add constraint fk_endereco_cidade foreign key (endereco_cidade_id) references cidades (id);
alter table pedidos add constraint fk_pedido_formas_pagamento foreign key (formas_pagamento_id) references formas_de_pagamento (id);
alter table pedidos add constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurantes (id);
alter table pedidos add constraint fk_pedido_usuario foreign key (usuario_id) references usuarios (id);
alter table produtos add constraint fk_produto_restaurante foreign key (restaurante_id) references restaurantes (id);
alter table restaurantes add constraint fk_restaurante_cozinha foreign key (cozinha_id) references cozinhas (id);
alter table restaurantes add constraint fk_endereco_cidade foreign key (endereco_cidade_id) references cidades (id);
alter table restaurantes_formas_de_pagamento add constraint fk_restaurante_formas_pagamento_formas_pagamento foreign key (formas_de_pagamento_id) references formas_de_pagamento (id);
alter table restaurantes_formas_de_pagamento add constraint fk_restaurante_formas_pagamento_restaurante foreign key (restaurante_id) references restaurantes (id);
alter table usuarios_grupos add constraint fk_usuario_grupo_grupo foreign key (grupo_id) references grupos (id);
alter table usuarios_grupos add constraint fk_usuario_grupo_usuario foreign key (usuario_id) references usuarios (id);
