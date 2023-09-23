insert into cozinhas(nome) values('Paraense');
insert into cozinhas(nome) values('Baiana');

insert into restaurantes(nome, taxa_frete, cozinha_id) values('Toca do Sabor', 5, 1);
insert into restaurantes(nome, taxa_frete, cozinha_id) values('Toca do Camarão', 7, 2);
insert into restaurantes(nome, taxa_frete, cozinha_id) values('Tia Sônia Restaurantes', 0, 2);

insert into formas_de_pagamentos(descricao) values('Dinheiro');
insert into formas_de_pagamentos(descricao) values('Pix');
insert into formas_de_pagamentos(descricao) values('Débito');
insert into formas_de_pagamentos(descricao) values('Crédito');

insert into permissoes(nome, descricao) values('Desenvolvedor', 'Pode fazer tudo');
insert into permissoes(nome, descricao) values('Gestor', 'Pode fazer quase tudo');
insert into permissoes(nome, descricao) values('Administrativo', 'Pode fazer pouca coisa');

insert into estados(nome, sigla) values('Tocantins', 'TO');
insert into estados(nome, sigla) values('Maranhão', 'MA');
insert into estados(nome, sigla) values('Goiás', 'GO');

insert into cidades(nome, estado_id) values('Itaguatins', 1);
insert into cidades(nome, estado_id) values('Palmas', 1);
insert into cidades(nome, estado_id) values('Imperatriz', 2);
insert into cidades(nome, estado_id) values('Goiânia', 1);