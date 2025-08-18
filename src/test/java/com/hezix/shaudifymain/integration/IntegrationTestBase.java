package com.hezix.shaudifymain.integration;

import com.hezix.shaudifymain.annotations.Integration;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Integration
@Testcontainers
@Sql({
        "classpath:sql/data.sql"
})
public abstract class IntegrationTestBase {
    @Container
    protected static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>("postgres:17-alpine")
                    .waitingFor(Wait.forListeningPort()) // Ждём открытия порта
                    .waitingFor(Wait.forLogMessage(".*database system is ready.*", 1)); // Ждём готовности БД
    @BeforeAll
    static void runContainer(){
        POSTGRES_CONTAINER.start();
    }
    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }
}
