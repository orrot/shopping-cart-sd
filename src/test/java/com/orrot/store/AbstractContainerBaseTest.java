package com.orrot.store;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractContainerBaseTest {

    public static final DockerImageName IMG_MY_SQL = DockerImageName.parse("mysql:8.4.3");
    public static MySQLContainer<?> MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer<>(IMG_MY_SQL);
        MY_SQL_CONTAINER = MY_SQL_CONTAINER
                .withDatabaseName("shopping-cart-test")
                .withUsername("user-test")
                .withPassword("password-test")
                .self();
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void additionalProperties(DynamicPropertyRegistry registry) {
        System.out.println("MySQL container is starting");
        System.out.println("--------------------------------------------------" + MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
    }
}
