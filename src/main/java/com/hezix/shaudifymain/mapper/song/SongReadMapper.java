package com.hezix.shaudifymain.mapper.song;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.mapper.Mapper;
import com.hezix.shaudifymain.mapper.user.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SongReadMapper implements Mapper<Song, ReadSongDto> {

    @Override
    public Song toEntity(ReadSongDto readSongDto) {
        return Song.builder()
                .id(readSongDto.getId())
                .title(readSongDto.getTitle())
                .description(readSongDto.getDescription())
                .createdAt(readSongDto.getCreatedAt())
                .creator(User.builder()
                        .id(readSongDto.getCreatorId())
                        .build())
                .album(Album.builder()
                        .id(readSongDto.getAlbumId())
                        .build())
                .build();
    }

    @Override
    public ReadSongDto toDto(Song song) {
        return ReadSongDto.builder()
                .id(song.getId())
                .title(song.getTitle())
                .description(song.getDescription())
                .createdAt(song.getCreatedAt())
                .creatorId(song.getCreator().getId())
                .albumId(song.getAlbum() != null ? song.getAlbum().getId() : null)
                .build();
    }

    public List<ReadSongDto> toDtoList(List<Song> songs) {
        return Optional.ofNullable(songs)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toDto)
                .toList();
    }
    public List<Song> toEntityList(List<ReadSongDto> dtoSongs) {
        return Optional.ofNullable(dtoSongs)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toEntity)
                .toList();
    }
}
