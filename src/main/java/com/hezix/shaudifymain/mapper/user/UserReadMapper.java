package com.hezix.shaudifymain.mapper.user;

import com.hezix.shaudifymain.entity.User;
import com.hezix.shaudifymain.entity.dto.user.ReadUserDto;
import com.hezix.shaudifymain.mapper.Mapper;

public class UserReadMapper implements Mapper<User, ReadUserDto> {
    @Override
    public User toEntity(ReadUserDto readUserDto) {
        return User.builder()
                .id(readUserDto.getId())
                .username(readUserDto.getUsername())
                .email(readUserDto.getEmail())
                .firstName(readUserDto.getFirstName())
                .lastName(readUserDto.getLastName())
                .role(readUserDto.getRole())
                .createdSong(readUserDto.getCreatedSong())
                .likedSongs(readUserDto.getLikedSong())
                .build();
    }

    @Override
    public ReadUserDto toDto(User user) {
        return ReadUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .createdSong(user.getCreatedSong())
                .likedSong(user.getLikedSongs())
                .build();
    }
}
