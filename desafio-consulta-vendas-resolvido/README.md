# Desafio Consulta Vendas

Implementação do desafio da DevSuperior – JPA, consultas SQL e JPQL.

## Consultas implementadas

- `GET /sales/report`
  - parâmetros opcionais `minDate`, `maxDate` e `name`;
  - retorno paginado com `id`, `date`, `amount` e `sellerName`;
  - `maxDate` ausente: data atual do sistema;
  - `minDate` ausente: um ano antes da data final;
  - `name` ausente: texto vazio.
- `GET /sales/summary`
  - parâmetros opcionais `minDate` e `maxDate`;
  - retorno com nome do vendedor e soma das vendas.
- As consultas usam JPQL + projections no `SaleRepository`.

## Postman

```text
GET /sales/summary?minDate=2022-01-01&maxDate=2022-06-30
GET /sales/summary
GET /sales/report
GET /sales/report?minDate=2022-05-01&maxDate=2022-05-31&name=odinson
```

O último teste deve retornar os registros de venda 9, 10 e 12, conforme o enunciado.

## Execução

Java 25 + Spring Boot 4.1.0.

```text
mvn spring-boot:run
```

Porta padrão: `8080`.

## Correção da carga de dados

A carga foi ajustada para que o teste do enunciado `GET /sales/report?minDate=2022-05-01&maxDate=2022-05-31&name=odinson` retorne os IDs 9, 10 e 12, conforme especificado no PDF.
