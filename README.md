#  Shopping Cart - Hexagonal Example

## Content Table

[How To Run The Project](#how-to-run-the-project)
[Other Commands](#other-commands)
[Assumptions and idea](#assumptions-and-idea)

## How to run the project

### Requirements

- Install Docker Desktop, Docker CE, Rancher Desktop or any other container runtime.
- Maven
- Java 21

### Steps

- Clone the repository
```
git clone https://github.com/orrot/shopping-cart-sd.git;
``` 

- Go to the root of the repo and start the required infrastructure (for the moment only a MySQL instance):
```
docker compose up -d
```

- Start the spring boot application
```
mvn spring-boot:run
```

- Open the browser and go to the following URL:
```
http://localhost:8080/swagger-ui/index.html#/
```

## Other commands

- To stop the infrastructure
```
docker compose down
```

- To compile executing all the unit and integration tests
```
mvn clean install
```

- To compile skipping integration tests
```
mvn clean install -DskipITs
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

The idea of this project is to show a simple example of a shopping cart using a hexagonal architecture. The guide for the implementation was mainly the book "Designing Hexagonal Architecture with Java" by "Davi Vieira", but of course there are elements from others authors and of course my own experience.




## Unit and integration tests




```mermaid
graph LR
A[Square Rect] -- Link text --> B((Circle))
A --> C(Round Rect)
B --> D{Rhombus}
C --> D
