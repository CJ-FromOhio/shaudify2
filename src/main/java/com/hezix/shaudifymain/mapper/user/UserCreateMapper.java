package com.hezix.shaudifymain.mapper.user;

import com.hezix.shaudifymain.entity.user.Role;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import com.hezix.shaudifymain.mapper.Mappable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCreateMapper implements Mappable<User, CreateUserDto> {
    @Override
    public User toEntity(CreateUserDto createUserDto) {
        return User.builder()
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .role(Role.USER)
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

                .build();
    }

    @Override
    public List<CreateUserDto> toDtoList(List<User> t) {
        return List.of();
    }

    @Override
    public List<User> toEntityList(List<CreateUserDto> f) {
        return List.of();
    }
}
