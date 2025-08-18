package com.hezix.shaudifymain.integration.service;

import com.hezix.shaudifymain.ShaudifyMainApplication;
import com.hezix.shaudifymain.annotations.Integration;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.integration.IntegrationTestBase;
import com.hezix.shaudifymain.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

public class UserServiceIntegrationTest extends IntegrationTestBase {
    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "taylor";

    @Autowired
    private UserService userService;

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
