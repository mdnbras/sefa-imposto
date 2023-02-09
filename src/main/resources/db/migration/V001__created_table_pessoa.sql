CREATE TABLE pessoa
(
    id       INT         NOT NULL AUTO_INCREMENT,
    cpf_cnpj VARCHAR(40) NOT NULL,
    nome     VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
);