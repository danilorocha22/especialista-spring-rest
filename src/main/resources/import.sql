insert into cozinhas(nome) values('Paraense');
insert into cozinhas(nome) values('Baiana');

insert into estados(nome, sigla) values('Tocantins', 'TO');
insert into estados(nome, sigla) values('Maranhão', 'MA');
insert into estados(nome, sigla) values('Goiás', 'GO');

insert into cidades(nome, estado_id) values('Itaguatins', 1);
insert into cidades(nome, estado_id) values('Palmas', 1);
insert into cidades(nome, estado_id) values('Imperatriz', 2);
insert into cidades(nome, estado_id) values('Goiânia', 1);

insert into restaurantes (id, nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_estado_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values (1, 'Toca do Sabor', 10, 1, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurantes (id, nome, taxa_frete, cozinha_id) values (2, 'Toca do Camarão', 9.50, 1);
insert into restaurantes (id, nome, taxa_frete, cozinha_id) values (3, 'Tia Sônia Restaurante', 15, 2);

insert into formas_de_pagamento(descricao) values('Dinheiro');
insert into formas_de_pagamento(descricao) values('Pix');
insert into formas_de_pagamento(descricao) values('Débito');
insert into formas_de_pagamento(descricao) values('Crédito');

insert into permissoes (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissoes (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_formas_de_pagamento(restaurante_id, formas_de_pagamento_id) values(1,1), (1,2), (1,3), (2,3),(3,2), (3,3);