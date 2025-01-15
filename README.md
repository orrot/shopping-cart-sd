#  Shopping Cart - Hexagonal Example
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

- Go to the root of the repo and start the required infrastructure:
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



## Content Table

[Technical Topic](readme/TECHNICAL.md)

```mermaid
graph LR
A[Square Rect] -- Link text --> B((Circle))
A --> C(Round Rect)
B --> D{Rhombus}
C --> D