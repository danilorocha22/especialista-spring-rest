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
values ('TO'), ('MA'), ('GO');

insert into cidades(nome, estado_id)
values ('Itaguatins', 1), ('Palmas', 1), ('Imperatriz', 2), ('Goiânia', 3);

insert ignore into cozinhas(nome)
values ('Brasileira'), ('Tailandesa'), ('Indiana'), ('Argentina');

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

insert into formas_de_pagamento(nome)
values ('Dinheiro'), ('Pix'), ('Débito'), ('Crédito');

insert into permissoes(nome, descricao)
values ('CONSULTAR_COZINHAS', 'Permite consultar cozinhas'), ('EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurantes_formas_de_pagamento(restaurante_id, formas_de_pagamento_id)
values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into produtos(nome, descricao, preco, ativo, restaurante_id)
values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1),
       ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 0, 1),
       ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso
        molho especial de pimenta vermelha', 87.20, 1, 2),
       ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 0, 3),
       ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3),
       ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do
        contrafilé', 79, 1, 4),
       ('T-Bone',  'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o
        filé mignon', 89, 1, 4),
       ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5),
       ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into grupos (id, nome)
values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

insert into grupos_permissoes (grupo_id, permissao_id)
values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert into usuarios(id, nome, email, senha, data_cadastro)
values (1, 'João da Silva', 'joao.ger@algafood.com', '123', utc_timestamp),
       (2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', utc_timestamp),
       (3, 'José Souza', 'jose.aux@algafood.com', '123', utc_timestamp),
       (4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', utc_timestamp),
       (5, 'Manoel Lima', 'manoel.loja@gmail.com', '123', utc_timestamp);

insert into usuarios_grupos (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2);

insert into restaurantes_usuarios_responsaveis (restaurante_id, usuario_id) values (1, 5), (3, 5);

insert into pedidos (id, codigo, restaurante_id, usuario_id, formas_pagamento_id, endereco_id, status, data_criacao,
                     sub_total, taxa_frete, valor_total)
values (1, '953b3e39-a35a-4aa9-bfbb-474192f7b825', 1, 1, 1, 1, 'CRIADO', utc_timestamp, 298.90, 10, 308.90);

insert into itens_pedido (id, pedido_id, produto_id, quantidade, preco_unit, valor_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null),
       (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into pedidos (id, codigo, restaurante_id, usuario_id, formas_pagamento_id, endereco_id, status, data_criacao,
                     sub_total, taxa_frete, valor_total)
values (2, '3b75fd6e-4a14-4721-8b19-b563c725302e', 4, 1, 2, 1, 'CRIADO', utc_timestamp, 79, 0, 79);

insert into itens_pedido (id, pedido_id, produto_id, quantidade, preco_unit, valor_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');