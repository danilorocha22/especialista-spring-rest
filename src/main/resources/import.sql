insert into cozinhas(nome) values('Paraense');
insert into cozinhas(nome) values('Baiana');
insert into cozinhas(nome) values('Tocantinense');
insert into cozinhas(nome) values('Maranhense');

insert into estados(nome, sigla) values('Tocantins', 'TO');
insert into estados(nome, sigla) values('Maranhão', 'MA');
insert into estados(nome, sigla) values('Goiás', 'GO');

insert into cidades(nome, estado_id) values('Itaguatins', 1);
insert into cidades(nome, estado_id) values('Palmas', 1);
insert into cidades(nome, estado_id) values('Imperatriz', 2);
insert into cidades(nome, estado_id) values('Goiânia', 3);

insert into restaurantes(nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_estado_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values('Toca do Sabor', 10, 1, utc_timestamp, utc_timestamp, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
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

insert into restaurante_formas_de_pagamento(restaurante_id, formas_de_pagamento_id) values(1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into produtos(nome, descricao, preco, ativo, restaurante_id) values('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);
