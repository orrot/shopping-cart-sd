#  Shopping Cart - Hexagonal Example

## Content Table

Yoy do not need to read all the document. Just "How To Run The Project" section is required and "Getting started".
The API is prepared so you can play with it using Swagger and without requiring DB access.
Even some data is created by default.

- [How To Run The Project](#how-to-run-the-project)
- [Getting Started](#getting-started)
- [Other Commands](#other-commands)
- [Assumptions and idea](#assumptions-and-idea)
- [Architecture](#architecture)
- [Unit tests and integration tests](#unit-tests-and-integration-tests)

## How to run the project

### Requirements

- Install Docker Desktop, Docker CE, Rancher Desktop or any other container runtime.
- Maven
- Java 21

### Steps

- Clone the repository
```bash
git clone https://github.com/orrot/shopping-cart-sd.git
``` 

- Go to the root of the repo and start the required infrastructure (for the moment only a MySQL instance):
```bash
docker compose up -d
```

The database with name `shopping-cart-sd` should be created automatically by the Compose file. 
The data should be stored in a host folder (volume) with name `dbdata` in the root folder. 

- To compile executing all the unit and integration tests
```bash
mvn clean install
```

- Start the spring boot application
```bash
mvn spring-boot:run
```

- Open the browser and go to the following URL:
```bash
http://localhost:8080/swagger-ui/index.html#/
```

- To compile skipping integration tests
```bash
mvn clean install -DskipITs
```

## Getting Started

The API already has some data created by default. The data is:

- 3 products
- 2 clients
- 3 payment methods

#### PRODUCTS

| ID | Name    | Curren Price    |
| :-----: | :---: | :---: |
| Banana | 2000   | Fruit by unit  |
| Orange | 1000   | Fruit by unit  |
| Strawberry | 2000   | Kg price   |

#### CLIENTS

| ID | Name    | Address |
|:--:| :---: |:-------:|
| 1  | Robotito   |    *    |
| 2  | Pablo Mármol   |    *    |

#### PAYMENT METHODS

|    Code    | Name    |   Fee Fixed   | Fee Percentage |
|:----------:| :---: |:-------------:|:--------------:|
| CASH   | Cash   |       0       |       0        | 
| MASTERCARD   | Mastercard Credit Card   | 800 |       4%       |
| VISA | Visa Credit Card   |   800    |       2%       |

Use them or create your own data to test the API.

## Other commands

- To stop the infrastructure
```
docker compose down
```

## Assumptions and idea

The idea is to use a pretty basic shopping cart API to show several concepts of the hexagonal architecture and the usage of some Java technologies.
The API is pretty simple, it can:

- Create a cart
- Add/Remove a product to a cart
- Replace the payment method of a cart
- Assign a client to a cart
- Replace the quantity of a product in the cart
- Get the total items of a cart
- Get the summary information of a cart
- Get the client that owns the cart and list its data

The API also contains other endpoints that allows to:
- CRUD operations over products
- CRUD operations over clients
- List the supported payment methods

The assumptions for those operations:

- The security of the application is not implemented yet. However, even after that, anyone could create a cart, even if the client is not registered.
- No payment operation is implemented, no tracking id field for a possible third party payment or things like that are implemented.
- Only one payment method is allowed per cart, as in pages like Amazon or Mercado Libre, however, the payment method can be changed.
- No i18n.
- No temporary user implemented. The client must be registered, however, it might not be linked to the Authentication.
- Logback is not configured
- Anyone could create a cart and having items. 
- A cart could exist without a registered client.
- A cart is created empty and the products are added/removed after that.
- A cart must have a payment method. The payment method should be setup when the cart is created.
- The products cannot be repeated, just the quantity is updated.
- The total number of items needs to be shown all the time in the UI.
- The payment methods can be added by database, however, the rule about fees because of the payment only can be configured by a percentage or a fix value. Behavior is fixed, nonetheless, easy to change.  
- Concurrent operations were not considered. Only default transaction type is used (which is no usually aggressive).

## Architecture

### Summary
The idea of this project is to show a simple example of a shopping cart using a hexagonal architecture.

  ```mermaid  
graph LR  
C((API client)) -- JSON Request --> A{Rest} --> I[Input Port] --> U[Use Case] --> S[Service-Entity] --> O[OutputPort] --> Z{Repository} --> D((MySQL))
```

API Client and the Database are external. We need a concrete implementation to support them.
Those are basically the adapters (input and output adapter).
We have a defined use cases based on the assumptions. Those use cases are implemented in the Input Ports, while the output ports are the interfaces that allow us to connect with the driven resources.

- API Client: It is like the actor that starts the conversation with the app.
- MySQL Instance: It is like the actor that the app initiate the conversation with.
- Input Adapter: Concrete implementation that allow us to talk in form of JSON and HTTP requests and connects with the Input Port.
- Input Port: The interface that defines the interaction with the driving actors (REST).
- UseCase: A particular feature of the app.
- Service-Entity: Rules and business concepts.
- Output Port: the interface that defines the interaction with the driven actors (MySQL).
- Output Adapter: Concrete implementation that allow us to talk with the Database in form of SQL statements.

### Details

- Models are not reused: There is a model for Rest/JSON, another that contains the POJO classes with non-anemic classes and another for Hibernate classes.
This increase the complexity, but allow also us to:

  - Know exactly what the API is receiving and sending.
  - Not combining Jackson/Swagger annotation and documentation with other classes
  - Having the Entities/Aggregates with the business rules, but without dealing with the Hibernate cycle.
  - Giving to hibernate the responsibility of only doing what is best: communication with the relational DB.

- There is no differences between Command and Queries. This could be desirable, but it is not implemented yet.
- Packages are organized by features, instead of just layers to avoid big packages.
- JPA Entities are built by using the bi-directional way. The circular dependency is avoided in the next layer.
- There are other approaches like using interfaces for the Domain to separate JPA from DDD Domain. 
- Builders do not provide all the behavior we might want. However, Lombok is still used to avoid boilerplate code.
- Open In View is Disabled. By default enabled is enabled in Spring Boot, but it brings undesirable behavior.


## Unit tests and integration tests

- Only cart folder is entirely covered by unit tests (ignoring some mappers conditional branches created by MapStruct).
- The integration tests were implemented for all the features, but just the Happy-Path
