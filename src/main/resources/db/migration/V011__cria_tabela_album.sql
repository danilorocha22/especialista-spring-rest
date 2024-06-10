CREATE TABLE album (
    produto_id bigint not null,
    nome_arquivo varchar(150) not null,
    descricao varchar(150),
    content_type varchar(80) not null,
    tamanho int not null,

    primary key (produto_id),
    constraint fk_album_produtos foreign key (produto_id) references produtos (id)
) engine = InnoDB default charset = utf8;