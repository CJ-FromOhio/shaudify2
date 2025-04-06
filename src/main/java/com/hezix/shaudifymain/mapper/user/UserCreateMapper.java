package com.hezix.shaudifymain.mapper.user;

import com.hezix.shaudifymain.entity.User;
import com.hezix.shaudifymain.entity.dto.user.CreateUserDto;
import com.hezix.shaudifymain.mapper.Mapper;

public class UserCreateMapper implements Mapper<User, CreateUserDto> {
    @Override
    public User toEntity(CreateUserDto createUserDto) {
        return User.builder()
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .role(createUserDto.getRole())

                .build();
    }

    @Override
    public CreateUserDto toDto(User user) {
        return CreateUserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())

                .build();
    }
}
