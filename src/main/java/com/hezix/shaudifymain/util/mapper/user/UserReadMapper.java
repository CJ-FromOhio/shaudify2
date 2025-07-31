package com.hezix.shaudifymain.util.mapper.user;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.util.mapper.Mappable;
import com.hezix.shaudifymain.util.mapper.album.AlbumReadMapper;
import com.hezix.shaudifymain.util.mapper.song.SongReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mappable<User, ReadUserDto> {
    private final SongReadMapper songReadMapper;

    private final AlbumReadMapper albumReadMapper;
    @Override
    public User toEntity(ReadUserDto readUserDto) {
        return User.builder()
                .id(readUserDto.getId())
                .username(readUserDto.getUsername())
                .email(readUserDto.getEmail())
                .firstName(readUserDto.getFirstName())
                .lastName(readUserDto.getLastName())
                .role(readUserDto.getRole())
                .createdSongs(songReadMapper.toEntityList(readUserDto.getCreatedSongs()))
                .likedSongs(songReadMapper.toEntitySet(readUserDto.getLikedSongs()))
                .albums(albumReadMapper.toEntityList(readUserDto.getAlbums()))
                .createdAt(readUserDto.getCreatedAt())
                .image(readUserDto.getImage())
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
                .createdSongs(songReadMapper.toDtoList(user.getCreatedSongs()))
                .likedSongs(songReadMapper.toDtoSet(user.getLikedSongs()))
                .albums(albumReadMapper.toDtoList(user.getAlbums()))
                .createdAt(user.getCreatedAt())
                .image(user.getImage())
                .build();
    }
    public List<ReadUserDto> toDtoList(List<User> users) {
        return Optional.ofNullable(users)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toDto)
                .toList();
    }
    public List<User> toEntityList(List<ReadUserDto> dtoUsers) {
        return Optional.ofNullable(dtoUsers)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toEntity)
                .toList();
    }
}
