package com.hezix.shaudifymain.mapper.user;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.mapper.Mapper;
import com.hezix.shaudifymain.mapper.album.AlbumReadMapper;
import com.hezix.shaudifymain.mapper.likedSong.LikedSongReadMapper;
import com.hezix.shaudifymain.mapper.song.SongReadMapper;
import com.hezix.shaudifymain.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, ReadUserDto> {
    private final SongReadMapper songReadMapper;
    private final LikedSongReadMapper likedSongReadMapper;
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
                .createdSong(songReadMapper.toEntityList(readUserDto.getCreatedSongs()))
                .likedSongs(likedSongReadMapper.toEntityList(readUserDto.getLikedSongs()))
                .albums(albumReadMapper.toEntityList(readUserDto.getAlbums()))
                .createdAt(readUserDto.getCreatedAt())
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
                .createdSongs(songReadMapper.toDtoList(user.getCreatedSong()))
                .likedSongs(likedSongReadMapper.toDtoList(user.getLikedSongs()))
                .albums(albumReadMapper.toDtoList(user.getAlbums()))
                .createdAt(user.getCreatedAt())
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
