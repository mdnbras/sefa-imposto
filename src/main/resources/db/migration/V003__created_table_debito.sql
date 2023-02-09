CREATE TABLE IF NOT EXISTS debito
(
    id         INT            NOT NULL AUTO_INCREMENT,
    id_imposto INT            NOT NULL,
    id_pessoa  INT            NOT NULL,
    valor      DECIMAL(10, 6) NOT NULL,
    PRIMARY KEY (id),
    INDEX fk_debito_pessoa_idx (id_pessoa ASC) VISIBLE,
    INDEX fk_debito_imposto1_idx (id_imposto ASC) VISIBLE,
    CONSTRAINT fk_debito_pessoa
        FOREIGN KEY (id_pessoa)
            REFERENCES pessoa (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_debito_imposto1
        FOREIGN KEY (id_imposto)
            REFERENCES imposto (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);