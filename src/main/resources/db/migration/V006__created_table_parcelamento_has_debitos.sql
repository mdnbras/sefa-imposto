CREATE TABLE IF NOT EXISTS parcelamento_has_debitos
(
    id_parcelamento INT NOT NULL,
    id_debito       INT NOT NULL,
    PRIMARY KEY (id_parcelamento, id_debito),
    INDEX fk_parcelamento_has_debito_debito1_idx (id_debito ASC) VISIBLE,
    INDEX fk_parcelamento_has_debito_parcelamento1_idx (id_parcelamento ASC) VISIBLE,
    CONSTRAINT fk_parcelamento_has_debito_parcelamento1
        FOREIGN KEY (id_parcelamento)
            REFERENCES parcelamento (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_parcelamento_has_debito_debito1
        FOREIGN KEY (id_debito)
            REFERENCES debito (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB