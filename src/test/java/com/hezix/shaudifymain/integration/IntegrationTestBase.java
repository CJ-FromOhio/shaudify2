package com.hezix.shaudifymain.integration;

import com.hezix.shaudifymain.annotations.Integration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@Integration
@Testcontainers
public class IntegrationTestBase {
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", PostgresSingletonContainer.get()::getJdbcUrl);
        registry.add("spring.datasource.username", PostgresSingletonContainer.get()::getUsername);
        registry.add("spring.datasource.password", PostgresSingletonContainer.get()::getPassword);

        registry.add("spring.data.redis.host", RedisSingletonContainer.get()::getHost);
        registry.add("spring.data.redis.port", () -> RedisSingletonContainer.get().getMappedPort(6379));
    }
    @BeforeAll
    static void beforeAll() {
        PostgresSingletonContainer.get().start();
        RedisSingletonContainer.get().start();
    }

    @AfterAll
    static void afterAll() {
        PostgresSingletonContainer.get().stop();
        RedisSingletonContainer.get().stop();
    }
}
