package com.orrot.store;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ActiveProfiles("test")
public class TestContainersTest extends AbstractContainerBaseTest {

    @Test
    public void myTestWithMySQL() {
        System.out.println("------------------- TEST 1");

    }
}
