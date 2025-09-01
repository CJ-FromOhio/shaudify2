package com.hezix.shaudifymain.integration.service;

import com.hezix.shaudifymain.annotations.Integration;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.integration.IntegrationTestBase;
import com.hezix.shaudifymain.integration.PostgresSingletonContainer;
import com.hezix.shaudifymain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
@Integration
@Testcontainers
public class UserServiceIntegrationTest {
    private static final Long USER_ID = 2L;
    private static final String USER_NAME = "weeknd";
    @Autowired
    private UserService userService;
    @Container
    private static PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:17-alpine")
            .waitingFor(Wait.forListeningPort())
            .waitingFor(Wait.forLogMessage(".*database system is ready.*", 1));
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }

    @Test
    void findById(){
        var expected = ReadUserDto.builder().id(USER_ID).build();
        var actual = userService.findUserById(USER_ID);
        System.out.println(actual);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByUsername(){
        var expected = ReadUserDto.builder().username(USER_NAME).build();
        expected.setId(USER_ID);
        var actual = userService.findUserByUsername(USER_NAME);
        System.out.println(actual);
        assertThat(actual).isEqualTo(expected);
    }
}
