package com.orrot.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
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
        log.info("MySQL container is starting");
        log.info("MySQL JDBC URL: {}", MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
    }
}
