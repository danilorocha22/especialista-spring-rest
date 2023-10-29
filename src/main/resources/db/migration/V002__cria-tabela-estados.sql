create table estados
(
    id    bigint      not null auto_increment,
    sigla varchar(2) not null,

    primary key (id)
) engine = InnoDB
  default charset = utf8;