package com.hezix.shaudifymain.integration;

import com.hezix.shaudifymain.annotations.Integration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class PostgresSingletonContainer {
    @Container
    public static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:17-alpine")
                .waitingFor(Wait.forListeningPort())
                .withReuse(true)
                .waitingFor(Wait.forLogMessage(".*database system is ready.*", 1));
        POSTGRES_CONTAINER.start();
    }
    public static PostgreSQLContainer<?> get() {
        return POSTGRES_CONTAINER;
    }

}
