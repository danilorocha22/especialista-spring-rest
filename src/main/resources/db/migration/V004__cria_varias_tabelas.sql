create table formas_de_pagamento(
                                    id        bigint       not null auto_increment,
                                    descricao varchar(60) not null,
                                    primary key (id)
) engine = InnoDB default charset=utf8;

create table grupos(
                       id   bigint       not null auto_increment,
                       nome varchar(60) not null,
                       primary key (id)
) engine = InnoDB default charset=utf8;

create table grupos_permissoes(
                                  grupo_id     bigint not null,
                                  permissao_id bigint not null,
                                  primary key (grupo_id, permissao_id)
) engine = InnoDB default charset=utf8;

create table permissoes(
                           id        bigint       not null auto_increment,
                           descricao varchar(60) not null,
                           nome      varchar(100) not null,
                           primary key (id)
) engine = InnoDB default charset=utf8;

create table produtos(
                         id             bigint         not null auto_increment,
                         nome           varchar(80)    not null,
                         ativo          tinyint(1)     not null,
                         preco          decimal(10, 2) not null,
                         restaurante_id bigint         not null,
                         descricao      text           not null,
                         primary key (id)
) engine = InnoDB default charset=utf8;

create table restaurantes(
                             id                   bigint         not null auto_increment,
                             cozinha_id           bigint         not null,
                             nome                 varchar(80)    not null,
                             taxa_frete           decimal(10, 2) not null,
                             data_atualizacao     datetime       not null,
                             data_cadastro        datetime       not null,

                             endereco_cidade_id   bigint,
                             endereco_bairro      varchar(60),
                             endereco_cep         varchar(9),
                             endereco_complemento varchar(60),
                             endereco_logradouro  varchar(100),
                             endereco_numero      varchar(20),
                             primary key (id)
) engine = InnoDB default charset=utf8;

create table restaurantes_formas_de_pagamento(
                                                 restaurante_id         bigint not null,
                                                 formas_de_pagamento_id bigint not null,
                                                 primary key (restaurante_id, formas_de_pagamento_id)
) engine = InnoDB default charset=utf8;

create table usuarios(
                         id            bigint       not null auto_increment,
                         nome          varchar(80)  not null,
                         email         varchar(255) not null,
                         senha         varchar(255) not null,
                         data_cadastro datetime(6)  not null,
                         primary key (id)
) engine = InnoDB default charset=utf8;

create table usuarios_grupos(
                                usuario_id bigint not null,
                                grupo_id   bigint not null,
                                primary key (usuario_id, grupo_id)
) engine = InnoDB default charset=utf8;

alter table grupos_permissoes add constraint fk_grupo_permissao_permissao
    foreign key (permissao_id) references permissoes (id);

alter table grupos_permissoes add constraint fk_grupo_permissao_grupo
    foreign key (grupo_id) references grupos (id);

alter table produtos add constraint fk_produto_restaurante
    foreign key (restaurante_id) references restaurantes (id);

alter table restaurantes add constraint fk_restaurante_cozinha
    foreign key (cozinha_id) references cozinhas (id);

alter table restaurantes add constraint fk_restaurante_cidade
    foreign key (endereco_cidade_id) references cidades (id);

alter table restaurantes_formas_de_pagamento add constraint fk_rest_forma_de_pagto_forma_de_pagto
    foreign key (formas_de_pagamento_id) references formas_de_pagamento (id);

alter table restaurantes_formas_de_pagamento add constraint fk_rest_forma_de_pagamento_restaurante
    foreign key (restaurante_id) references restaurantes (id);