package com.hezix.shaudifymain.integration.service;

import com.hezix.shaudifymain.BaseIntegrationTest;
import com.hezix.shaudifymain.annotations.Integration;
import com.hezix.shaudifymain.entity.user.Role;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.repository.UserRepository;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.hezix.shaudifymain.util.filter.UserFilter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Integration
@Testcontainers
public class UserServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private Long savedUserId;
    private String savedUsername;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        String uid = UUID.randomUUID().toString().substring(0, 8);
        var user = userRepository.saveAndFlush(User.builder()
                .username("user_" + uid)
                .email(uid + "@test.com")
                .password("password")
                .role(Role.USER)
                .createdAt(Instant.now())
                .build());

        this.savedUserId = user.getId();
        this.savedUsername = user.getUsername();
    }

    @Test
    void findById() {
        var actual = userService.findUserById(savedUserId);

        assertThat(actual.getId()).isEqualTo(savedUserId);
        assertThat(actual.getUsername()).isEqualTo(savedUsername);
    }

    @Test
    void findByUsername() {
        var actual = userService.findUserByUsername(savedUsername);

        assertThat(actual.getId()).isEqualTo(savedUserId);
        assertThat(actual.getUsername()).isEqualTo(savedUsername);
    }

    @Test
    void findById_throwIfNotExists() {
        assertThrows(EntityNotFoundException.class,
                () -> userService.findUserById(-1L));
    }

    @Test
    void findByUsername_throwIfNotExists() {
        assertThrows(EntityNotFoundException.class,
                () -> userService.findUserByUsername("non_existent_user"));
    }

    @Test
    void findAll() {
        userRepository.save(User.builder()
                .username("another_user")
                .email("another@test.com")
                .role(Role.USER)
                .build());

        var actual = userService.findAllUsers();
        assertThat(actual).hasSize(2);
    }

    @Test
    void findAll_byFilter() {
        Pageable pageable = PageRequest.of(0, 10);
        var filter = new UserFilter(null, null, savedUsername, Role.USER);

        var actual = userService.findAllUsersByFilter(filter, pageable);

        assertThat(actual.getContent()).hasSize(1);
        assertThat(actual.getContent().get(0).getUsername()).isEqualTo(savedUsername);
    }

    @Test
    void user_saving() {
        String uniqueName = "new_user_" + UUID.randomUUID().toString().substring(0, 5);
        var createDto = CreateUserDto.builder()
                .username(uniqueName)
                .password("testPass")
                .passwordConfirm("testPass")
                .firstName("John")
                .lastName("Doe")
                .email(uniqueName + "@mail.com")
                .build();

        ReadUserDto saved = userService.save(createDto);

        assertThat(saved.getId()).isNotNull();
        assertThat(userService.findUserById(saved.getId()).getUsername()).isEqualTo(uniqueName);
    }
}