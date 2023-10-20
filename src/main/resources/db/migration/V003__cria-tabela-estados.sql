create table estados
(
    id   bigint      not null auto_increment,
    nome varchar(80) not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8;

insert into estados(nome)
select distinct nome_estado
from cidades;

alter table cidades
    add column estado_id bigint not null;

update cidades c
set c.estado_id = (select e.id from estados e where e.nome = c.nome_estado);

alter table cidades
    add constraint fk_cidade_estado
        foreign key (estado_id) references estados (id);

alter table cidades drop column nome_estado;

alter table cidades change nome_cidade nome varchar(80) not null;