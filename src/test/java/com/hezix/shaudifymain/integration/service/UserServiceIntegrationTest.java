package com.hezix.shaudifymain.integration.service;

import com.hezix.shaudifymain.annotations.Integration;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.user.Role;
import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.hezix.shaudifymain.util.filter.SongFilter;
import com.hezix.shaudifymain.util.filter.UserFilter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Integration
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceIntegrationTest {
    private static final Long USER_ID = 2L;
    private static final Long USER_NON_EXISTENT_ID = 9999L;
    private static final String USER_NON_EXISTENT_USERNAME = "james";
    private static final String USER_NAME = "weeknd";
    private static final String USERNAME_FOR_FILTER = "";
    private static final String FIRSTNAME_FOR_FILTER = "";
    private static final String LASTNAME_FOR_FILTER = "";
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
    @Test
    void findById_throwIfNotExists(){
        assertThrows(EntityNotFoundException.class,
                () -> userService.findUserById(USER_NON_EXISTENT_ID));
    }
    @Test
    void findByUsername_throwIfNotExists(){
        assertThrows(EntityNotFoundException.class,
                () -> userService.findUserByUsername(USER_NON_EXISTENT_USERNAME));
    }
    @Test
    @Order(1)
    void findAll(){
        int expected = 7;
        var actual = userService.findAllUsers();
        assertThat(actual).hasSize(expected);
    }
    @Test
    @Order(2)
    void findAll_byFilter(){
        int expected = 7;
        Pageable pageable = PageRequest.of(0, 15);
        var actual = userService.findAllUsersByFilter(new UserFilter(FIRSTNAME_FOR_FILTER,
                                                                     LASTNAME_FOR_FILTER,
                                                                     USERNAME_FOR_FILTER, Role.USER), pageable);
        assertThat(actual).hasSize(expected);
    }
    @Order(3)
    @Test
    void user_saving(){
        var expected = userService.save(CreateUserDto.builder()
                        .username("testUser")
                        .password("testPassword")
                        .passwordConfirm("testPassword")
                        .firstName("testFirstName")
                        .lastName("testLastName")
                        .email("testEmail")
                .build());
        var actual = userService.findUserById(expected.getId());
        assertThat(actual).isEqualTo(expected);
    }
}
