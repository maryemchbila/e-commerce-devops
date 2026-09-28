# E-Commerce Backend

A simple Spring Boot REST API that serves products from an in-memory list.

## Requirements

- Java 17
- Maven

## Run locally

From the `backend` directory, run:

```shell
mvn spring-boot:run
```

The API starts at `http://localhost:8080`.

## Test the API

List all products:

```shell
curl http://localhost:8080/api/products
```

Get one product:

```shell
curl http://localhost:8080/api/products/1
```

Run the automated tests:

```shell
mvn test
```
