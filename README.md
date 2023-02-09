## Sobre
- O desafio foi desenvolvido com Spring Boot, JPA, Mysql e Java 17. Foi utilizado uma arquitetura baseada em DDD, com aplicação de Clean Code e Testes de Serviço. 
## API

### Permitir listar os débitos de uma pessoa;

- Endpoint: `GET http://localhost:8090/v1/pessoa/{idPessoa}/meus-debitos`
- Path Variables:

| Key      | Value | Description  |
|----------|-------|--------------|
| idPessoa | 1     | Id de Pessoa |

- Response 200 ( OK ):
```json
[
    {
        "id": 1,
        "imposto": {
            "id": 1,
            "descricao": "IPVA"
        },
        "pessoa": {
            "id": 1,
            "cpfCnpj": "51898687021",
            "nome": "Pessoa A"
        },
        "valor": 200.000000
    },
    {
        "id": 2,
        "imposto": {
            "id": 1,
            "descricao": "IPVA"
        },
        "pessoa": {
            "id": 1,
            "cpfCnpj": "51898687021",
            "nome": "Pessoa A"
        },
        "valor": 300.000000
    }
]
```

### Criar um parcelamento para os débitos de uma pessoa;

- Endpoint: `POST http://localhost:8090/v1/parcelamento`
- Request Body (JSON):
```json
{
  "idPessoa": 1,
  "qtdParcelasTotais": 2,
  "valorParcela": 200.0,
  "situacao": "ATIVO",
  "listDebitos": [1]
}
```
- Response 201 (CREATED): 

`No content`
- Response 500 (erros mapeados):
```json
{
    "timestamp": "2023-02-09 18:52:22",
    "error": "exception.parcelamento.valor.parcela.min",
    "message": "Limite minimo da parcela foi atingido"
}
```
```json
{
  "timestamp": "2023-02-09 18:54:35",
  "error": "exception.parcelamento.imposto.diferente",
  "message": "Imposto diferentes para o um parcelamento não é permitido"
}
```
```json
{
  "timestamp": "2023-02-09 18:56:06",
  "error": "exception.parcelamento.qtd.parcelas.limit",
  "message": "Limite de parcelas atiginda para um Parcelamento"
}
```

### Listar os parcelamentos de uma pessoa;

- Endpoint: `POST http://localhost:8090/v1/pessoa/{idPessoa}/meus-parcelamentos`

- Path Variables:

| Key      | Value | Description  |
|----------|-------|--------------|
| idPessoa | 1     | Id de Pessoa |

- Response 200 ( OK ):

```json
[
    {
        "id": 1,
        "idPessoa": 1,
        "qtdParcelasTotais": 2,
        "qtdParcelasPagas": 0,
        "valorParcela": 100.000000,
        "situacao": "ATIVO",
        "listDebitos": null,
        "parcelas": [
            {
                "id": 1,
                "valor": 100.000000,
                "numero": 1,
                "pago": false
            },
            {
                "id": 2,
                "valor": 100.000000,
                "numero": 2,
                "pago": false
            }
        ]
    }
]
```

### Cancelar um parcelamento de uma pessoa;

- Endpoint: `POST http://localhost:8090/v1/parcelamento/cancelar/{id}`

- Path Variables:

| Key | Value | Description        |
|-----|-------|--------------------|
| id  | 1     | Id do Parcelamento |

- Response 200:

`No content`


### Permitir receber o pagamento de uma parcela de um parcelamento;

- Endpoint: `POST http://localhost:8090/v1/parcelamento/efetuar-pagamento`
- Request Body (JSON):
```json
{
  "idParcelamento": 1,
  "numeroParcela": 1,
  "valorPago": 100.00
}
```

- Response 200:

`No content`