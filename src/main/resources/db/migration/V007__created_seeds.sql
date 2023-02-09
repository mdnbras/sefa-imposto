insert into pessoa (id, cpf_cnpj, nome)
VALUES (1, '51898687021', 'Pessoa A'),
       (2, '61263268000100', 'Pessoa B'),
       (3, '40013945000162', 'Pessoa C'),
       (4, '22803878089', 'Pessoa D');

insert into imposto (id, descricao)
VALUES (1, 'IPVA'),
       (2, 'ICMS');

insert into debito (id, id_imposto, id_pessoa, valor)
values (1, 1, 1, 200),
       (2, 2, 1, 300),
       (3, 2, 2, 500);