## Arquivo de callback
## Após o Flyway finalizaras as migrations, este arquivo será executado

## Apaga todos os registros
set foreign_key_checks = 0; # Desabilita as chaves estrangeiras para poder apagar os registros
delete from cidades;
delete from cozinhas;
delete from estados;
delete from formas_de_pagamento;
delete from grupos;
delete from grupos_permissoes;
delete from permissoes;
delete from produtos;
delete from enderecos;
delete from restaurantes;
delete from restaurantes_formas_de_pagamento;
delete from usuarios;
delete from usuarios_grupos;

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

insert into estados(sigla) values('TO');
insert into estados(sigla) values('MA');
insert into estados(sigla) values('GO');

insert into cidades(nome, estado_id) values('Itaguatins', 1);
insert into cidades(nome, estado_id) values('Palmas', 1);
insert into cidades(nome, estado_id) values('Imperatriz', 2);
insert into cidades(nome, estado_id) values('Goiânia', 3);

insert ignore into cozinhas(nome) values('Brasileira');
insert ignore into cozinhas(nome) values('Tailandesa');
insert ignore into cozinhas(nome) values('Indiana');
insert ignore into cozinhas(nome) values('Argentina');

insert into enderecos(logradouro, numero, bairro, cep, cidade_id) values ('Rua Cel. Augusto Bastos', '1000', 'Centro', '77920-000', 1);
insert into enderecos(logradouro, numero, bairro, cep, cidade_id) values ('Rua 26, quadra 28', '2000', 'Centro', '77062-060', 2);
insert into enderecos(logradouro, numero, bairro, cep, cidade_id) values ('Rua Getúlio Vargas', '50', 'Centro', '88999-000', 3);
insert into enderecos(logradouro, numero, bairro, cep, cidade_id) values ('Rua João Pinheiro', '1000', 'Centro', '22666-000', 4);

insert into restaurantes(nome, taxa_frete, cozinha_id, endereco_id, data_cadastro, data_atualizacao) values('Toca do Sabor', 10, 1, 1, utc_timestamp, utc_timestamp);
insert into restaurantes(nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values('Toca do Camarão', 9.50, 1, utc_timestamp, utc_timestamp);
insert into restaurantes(nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values('Tia Sônia Restaurante', 15, 2, utc_timestamp, utc_timestamp);
insert into restaurantes(nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values('Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp);
insert into restaurantes(nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values('Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp);
insert into restaurantes(nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values('Bar da Maria', 6, 4, utc_timestamp, utc_timestamp);

insert into formas_de_pagamento(descricao) values('Dinheiro');
insert into formas_de_pagamento(descricao) values('Pix');
insert into formas_de_pagamento(descricao) values('Débito');
insert into formas_de_pagamento(descricao) values('Crédito');

insert into permissoes(nome, descricao) values('CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissoes(nome, descricao) values('EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurantes_formas_de_pagamento(restaurante_id, formas_de_pagamento_id) values(1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 0, 1);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 0, 3);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

