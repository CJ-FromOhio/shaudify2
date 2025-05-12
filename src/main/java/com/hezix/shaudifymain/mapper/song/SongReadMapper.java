package com.hezix.shaudifymain.mapper.song;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.mapper.Mapper;
import com.hezix.shaudifymain.mapper.user.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SongReadMapper implements Mapper<Song, ReadSongDto> {

    private final UserReadMapper userReadMapper;

    @Override
    public Song toEntity(ReadSongDto readSongDto) {
        return Song.builder()
                .id(readSongDto.getId())
                .title(readSongDto.getTitle())
                .description(readSongDto.getDescription())
                .createdAt(readSongDto.getCreatedAt())
                .creator(userReadMapper.toEntity(readSongDto.getCreator()))
                .build();
    }

    @Override
    public ReadSongDto toDto(Song song) {
        return ReadSongDto.builder()
                .id(song.getId())
                .title(song.getTitle())
                .description(song.getDescription())
                .createdAt(song.getCreatedAt())
                .creator(userReadMapper.toDto(song.getCreator()))
                .build();
    }

    public List<ReadSongDto> toDtoList(List<Song> songs) {
        return songs.stream()
                .map(song -> new SongReadMapper(userReadMapper).toDto(song))
                .toList();
    }
    public List<Song> toEntityList(List<ReadSongDto> dtoSongs) {
        return dtoSongs.stream()
                .map(song -> new SongReadMapper(userReadMapper).toEntity(song))
                .toList();
    }
}
