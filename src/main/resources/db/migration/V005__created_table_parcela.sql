CREATE TABLE parcela
(
    id              INT            NOT NULL AUTO_INCREMENT,
    id_parcelamento INT            NOT NULL,
    valor_parcela   DECIMAL(10, 6) NOT NULL,
    numero_parcela  INT            NOT NULL,
    pago            TINYINT        NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    INDEX fk_parcela_parcelamento1_idx (id_parcelamento ASC) VISIBLE,
    CONSTRAINT fk_parcela_parcelamento1
        FOREIGN KEY (id_parcelamento)
            REFERENCES parcelamento (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)