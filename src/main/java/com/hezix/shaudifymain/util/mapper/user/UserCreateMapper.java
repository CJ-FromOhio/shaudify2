package com.hezix.shaudifymain.util.mapper.user;

import com.hezix.shaudifymain.entity.user.Role;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import com.hezix.shaudifymain.util.mapper.Mappable;
import org.springframework.stereotype.Component;

import java.time.Instant;
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
                .createdAt(Instant.now())
                .build();
    }

    @Override
    public CreateUserDto toDto(User user) {
        return CreateUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())

                .build();
    }


    public List<CreateUserDto> toDtoList(List<User> t) {
        return List.of();
    }


    public List<User> toEntityList(List<CreateUserDto> f) {
        return List.of();
    }
}
