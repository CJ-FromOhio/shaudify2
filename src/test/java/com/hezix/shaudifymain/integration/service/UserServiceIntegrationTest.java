package com.hezix.shaudifymain.integration.service;

import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.integration.IntegrationTestBase;
import com.hezix.shaudifymain.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceIntegrationTest extends IntegrationTestBase {
    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "taylor";

    @Autowired
    private UserService userService;

    @Test
    @Sql("classpath:sql/data.sql")
    void findById(){
        var expected = ReadUserDto.builder().id(USER_ID).build();
        var actual = userService.findUserById(USER_ID);
        System.out.println(actual);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Sql("classpath:sql/data.sql")
    void findByUsername(){
        var expected = ReadUserDto.builder().username(USER_NAME).build();
        expected.setId(USER_ID);
        var actual = userService.findUserByUsername(USER_NAME);
        System.out.println(actual);
        assertThat(actual).isEqualTo(expected);
    }
}
