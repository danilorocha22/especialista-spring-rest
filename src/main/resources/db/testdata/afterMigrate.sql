## Arquivo de callback
## Após o Flyway finalizaras as migrations, este arquivo será executado

## Apaga todos os registros
set foreign_key_checks = 0; # Desabilita as chaves estrangeiras para poder apagar os registros
delete from cidades where true;
delete from cozinhas where true;
delete from estados where true;
delete from formas_de_pagamento where true;
delete from grupos where true;
delete from grupos_permissoes where true;
delete from permissoes where true;
delete from produtos where true;
delete from enderecos where true;
delete from restaurantes where true;
delete from restaurantes_formas_de_pagamento where true;
delete from restaurantes_usuarios_responsaveis where true;
delete from usuarios where true;
delete from usuarios_grupos where true;
delete from pedidos where true;
delete from itens_pedido where true;
delete from album where true;

## Cria os registros novamente
set foreign_key_checks = 1; # Habilita as chaves estrangeiras para poder criar os registros
alter table cidades auto_increment = 1; # O auto_increment=1 reinicia os ids para 1
alter table cozinhas auto_increment = 1;
alter table estados auto_increment = 1;
alter table formas_de_pagamento auto_increment = 1;
alter table grupos auto_increment = 1;
alter table permissoes auto_increment = 1;
alter table produtos auto_increment = 1;
alter table enderecos auto_increment = 1;
alter table restaurantes auto_increment = 1;
alter table usuarios auto_increment = 1;
alter table pedidos auto_increment = 1;
alter table itens_pedido auto_increment= 1;

insert into estados(sigla)
values ('TO'),
       ('MA'),
       ('GO');

insert into cidades(nome, estado_id)
values ('Itaguatins', 1),
       ('Palmas', 1),
       ('Imperatriz', 2),
       ('Goiânia', 3);

insert ignore into cozinhas(nome)
values ('Brasileira'),
       ('Tailandesa'),
       ('Indiana'),
       ('Argentina');

insert into enderecos(logradouro, numero, bairro, cep, cidade_id)
values ('Rua Cel. Augusto Bastos', '1000', 'Centro', '77920-000', 1),
       ('Rua 26, quadra 28', '2000', 'Centro', '77062-060', 2),
       ('Rua Getúlio Vargas', '50', 'Centro', '88999-000', 3),
       ('Rua João Pinheiro', '1000', 'Centro', '22666-000', 4);

insert into restaurantes(nome, taxa_frete, cozinha_id, endereco_id, data_cadastro, data_atualizacao)
values ('Toca do Sabor', 10, 1, 1, utc_timestamp, utc_timestamp),
       ('Toca do Camarão', 9.50, 1, null, utc_timestamp, utc_timestamp),
       ('Tia Sônia Restaurante', 15, 2, null, utc_timestamp, utc_timestamp),
       ('Java Steakhouse', 12, 3, null, utc_timestamp, utc_timestamp),
       ('Lanchonete do Tio Sam', 11, 4, null, utc_timestamp, utc_timestamp),
       ('Bar da Maria', 6, 4, null, utc_timestamp, utc_timestamp);

insert into formas_de_pagamento(nome, data_atualizacao)
values ('Dinheiro', utc_timestamp),
       ('Pix', utc_timestamp),
       ('Débito', utc_timestamp),
       ('Crédito', utc_timestamp);

insert into permissoes(id, nome, descricao)
values (1,'EDITAR_COZINHAS', 'Permite editar cozinhas'),
       (2,'EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento'),
       (3,'EDITAR_CIDADES', 'Permite criar ou editar cidades'),
       (4,'EDITAR_ESTADOS', 'Permite criar ou editar estados'),
       (5,'CONSULTAR_USUARIOS', 'Permite consultar usuários'),
       (6,'EDITAR_USUARIOS', 'Permite criar ou editar usuários'),
       (7,'EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes'),
       (8,'CONSULTAR_PEDIDOS', 'Permite consultar pedidos'),
       (9,'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos'),
       (10,'GERAR_RELATORIOS', 'Permite gerar relatórios');

insert into restaurantes_formas_de_pagamento(restaurante_id, formas_de_pagamento_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 3),
       (3, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (5, 1),
       (5, 2),
       (6, 3);

insert into produtos(nome, descricao, preco, ativo, restaurante_id)
values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1),
       ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 0, 1),
       ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2),
       ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 0, 3),
       ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3),
       ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4),
       ('T-Bone',  'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4),
       ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5),
       ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into grupos (id, nome)
values (1, 'Gerente'),
       (2, 'Vendedor'),
       (3, 'Auxiliar'),
       (4, 'Cadastrador');

/*insert into grupos_permissoes (grupo_id, permissao_id)
values (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (3, 1);*/

insert into usuarios(id, nome, email, senha, data_cadastro)
values (1, 'Danilo Rocha', 'danrocha858585+ger@gmail.com', '$2a$12$cMTXqDlfPAU1jtWUpYnDj./B0fGfYGDmLYIlpgjWc71fhgYlLjI/q', utc_timestamp),
       (2, 'José Souza', 'danrocha858585+jose_vend@gmail.com', '$2a$12$cMTXqDlfPAU1jtWUpYnDj./B0fGfYGDmLYIlpgjWc71fhgYlLjI/q', utc_timestamp),
       (3, 'Maria Joaquina', 'danrocha858585+maria_sec@gmail.com', '$2a$12$cMTXqDlfPAU1jtWUpYnDj./B0fGfYGDmLYIlpgjWc71fhgYlLjI/q', utc_timestamp),
       (4, 'Sebastião Martins', 'danrocha858585+sebastiao_cad@gmail.com', '$2a$12$cMTXqDlfPAU1jtWUpYnDj./B0fGfYGDmLYIlpgjWc71fhgYlLjI/q', utc_timestamp),
       (5, 'Manoel Lima', 'danrocha858585+manoel_responsavel@gmail.com', '$2a$12$cMTXqDlfPAU1jtWUpYnDj./B0fGfYGDmLYIlpgjWc71fhgYlLjI/q', utc_timestamp),
       (6, 'Thiago Santos', 'danrocha858585+thiago_cliente@gmail.com', '$2a$12$cMTXqDlfPAU1jtWUpYnDj./B0fGfYGDmLYIlpgjWc71fhgYlLjI/q', utc_timestamp),
       (7, 'Joaquim Mendes', 'danrocha858585+joaquim_cliente@gmail.com', '$2a$12$cMTXqDlfPAU1jtWUpYnDj./B0fGfYGDmLYIlpgjWc71fhgYlLjI/q', utc_timestamp),
       (8, 'Rita Dias', 'danrocha858585+rita_cliente@gmail.com', '$2a$12$cMTXqDlfPAU1jtWUpYnDj./B0fGfYGDmLYIlpgjWc71fhgYlLjI/q', utc_timestamp);

insert into usuarios_grupos (usuario_id, grupo_id)
values (1, 1), #gerente
       (2, 2), #vendedor
       (3, 3), #secretária
       (4, 4); #cadastrador

# Adiciona todas as permissoes no grupo do gerente
insert into dan_food.grupos_permissoes (grupo_id, permissao_id)
select 1, id from permissoes;

# Adiciona permissoes no grupo do vendedor
insert into grupos_permissoes (grupo_id, permissao_id)
select 2, id from permissoes where nome like 'CONSULTAR_%';

#insert into grupos_permissoes (grupo_id, permissao_id) values (2, 14);

# Adiciona permissoes no grupo do auxiliar
insert into grupos_permissoes (grupo_id, permissao_id)
select 3, id from permissoes where nome like 'CONSULTAR_%';

# Adiciona permissoes no grupo cadastrador
insert into grupos_permissoes (grupo_id, permissao_id)
select 4, id from permissoes
where nome like '%_RESTAURANTES' or nome like '%_PRODUTOS';

insert into restaurantes_usuarios_responsaveis (restaurante_id, usuario_id)
values (1, 5),
       (3, 5);

insert into pedidos (id, codigo, restaurante_id, usuario_id, formas_pagamento_id, endereco_id, status, data_criacao,data_confirmacao, data_entrega, sub_total, taxa_frete, valor_total)
values (1, '953b3e39-a35a-4aa9-bfbb-474192f7b825', 1, 6, 1, 1, 'CRIADO', utc_timestamp, null, null, 298.90, 10, 308.90),
       (2, '3b75fd6e-4a14-4721-8b19-b563c725302e', 4, 6, 2, 1, 'CRIADO', utc_timestamp, null, null, 79, 0, 79),
       (3, 'f9981ca4-5a5e-4da3-af04-933861df3e55', 2, 6, 1, 1, 'CONFIRMADO', DATE_ADD(utc_timestamp(), INTERVAL 1 DAY), DATE_ADD(utc_timestamp(), INTERVAL 2 DAY), null, 298.90, 10, 308.90),
       (4, 'd178b637-a785-4768-a3cb-aa1ce5a8cdab', 4, 7, 2, 1, 'CONFIRMADO', DATE_ADD(utc_timestamp(), INTERVAL 3 DAY), DATE_ADD(utc_timestamp(), INTERVAL 4 DAY), null, 79, 0, 79),
       (5, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 7, 1, 1, 'ENTREGUE', DATE_ADD(utc_timestamp(), INTERVAL 7 DAY), DATE_ADD(utc_timestamp(), INTERVAL 8 DAY), DATE_ADD(utc_timestamp(), INTERVAL 8 DAY), 110, 10, 120),
       (6, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 3, 8, 1, 1, 'ENTREGUE', DATE_ADD(utc_timestamp(), INTERVAL 7 DAY), DATE_ADD(utc_timestamp(), INTERVAL 8 DAY), DATE_ADD(utc_timestamp(), INTERVAL 9 DAY), 174.4, 5, 179.4),
       (7, '8d774bcf-b238-42f3-aef1-5fb388754d63', 4, 8, 2, 1, 'ENTREGUE',  DATE_ADD(utc_timestamp(), INTERVAL 7 DAY), DATE_ADD(utc_timestamp(), INTERVAL 8 DAY), DATE_ADD(utc_timestamp(), INTERVAL 9 DAY), 87.2, 10, 97.2);


insert into itens_pedido (id, pedido_id, produto_id, quantidade, preco_unit, valor_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null),
       (2, 1, 2, 2, 110, 220, 'Menos picante, por favor'),
       (3, 2, 1, 1, 78.9, 78.9, null),
       (4, 2, 6, 1, 79, 79, 'Ao ponto'),
       (5, 3, 6, 1, 79, 79, 'Ao ponto'),
       (6, 3, 2, 2, 110, 220, 'Menos picante, por favor'),
       (7, 5, 2, 1, 110, 110, null),
       (8, 6, 3, 2, 87.2, 174.4, null),
       (9, 7, 3, 1, 87.2, 87.2, null);