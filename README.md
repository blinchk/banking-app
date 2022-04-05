# Accounts Banking Application

The tasks consist of implementing a small core banking solution:

- Account to keep track of current accounts, balances, and transaction history
- Capability to publish messages into RabbitMQ for other consumers

## Technologies

* Java 17 (OpenJDK 17.0.2)
* Spring Boot 2.5.6
* Hibernate
* Gradle
* PostgreSQL
* RabbitMQ
* JUnit

## Instructions

### Build

Solution can be easily built with Gradle, use `gradle build` or `./gradlew build` for it.

### Run

To run solution with Docker Compose use `docker-compose up -d`. Database and RabbitMQ included in Docker Compose.

### Test

To run tests do not forget to enable RabbitMQ service on your machine or in Docker container. PostgreSQL is not
required, due to enabled H2 database on testing.

Use `gradle test` or `./gradlew test` for it.

## Endpoints

* `GET` /account/{id}
* `POST` /account
* `GET` /transaction/{id}
* `POST` /transaction

## Load Tests Results

| Requests (per thread) | Threads | Result | Per one request |
|-----------------------|---------|--------|-----------------|
| 10                    | 1       | 76     | 13.03995ms      |  
| 1                     | 10      | 86     | 13.694ms        |
| 50                    | 10      | 97     | 10.25685ms      |  

## Choices

### Hibernate

In integration testing I am using H2 database, instead of PostgreSQL.
Hibernate can easier work with several types of databases, due to HQL.

> Anyway, I am open to learn it.

### Why tests excluded from gradle "build task"?

Unfortunately, I didn't found correct way to mock AMQP beans.

