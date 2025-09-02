package com.hezix.shaudifymain.unit.service;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.repository.UserRepository;
import com.hezix.shaudifymain.service.user.UserService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "Johnson1";
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Test
    void findUserByIdTest(){
       given(userRepository.findById(USER_ID))
               .willReturn(Optional.of(User.builder().id(USER_ID).build()));
        var expected = User.builder().id(USER_ID).build();
        assertThat(userService.findUserEntityById(USER_ID)).isEqualTo(expected);
    }
    @Test
    void findUserByUsernameTest(){
        given(userRepository.findByUsername(USER_NAME))
                .willReturn(Optional.of(User.builder().username(USER_NAME).build()));
        var expected = User.builder().username(USER_NAME).build();
        assertThat(userService.findUserEntityByUsername(USER_NAME)).isEqualTo(expected);
    }
}
