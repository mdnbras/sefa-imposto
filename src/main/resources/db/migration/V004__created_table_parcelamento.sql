CREATE TABLE IF NOT EXISTS parcelamento
(
    id                    INT                         NOT NULL AUTO_INCREMENT,
    id_pessoa             INT                         NOT NULL,
    qtd_parcelas_totais   INT                         NOT NULL,
    qtd_parcelas_pagas    INT                         NOT NULL,
    valor_total_parcelado DECIMAL(10, 6)              NOT NULL,
    valor_parcela         DECIMAL(10, 6)              NOT NULL,
    situacao              ENUM ('ATIVO', 'CANCELADO') NOT NULL,
    criado_em             DATETIME                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX fk_parcelamento_pessoa1_idx (id_pessoa ASC) VISIBLE,
    CONSTRAINT fk_parcelamento_pessoa1
        FOREIGN KEY (id_pessoa)
            REFERENCES pessoa (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);