package com.hezix.shaudifymain.mapper.song;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SongReadMapper implements Mapper<Song, ReadSongDto> {
    @Override
    public Song toEntity(ReadSongDto readSongDto) {
        return Song.builder()
                .title(readSongDto.getTitle())
                .description(readSongDto.getDescription())
                .createdAt(readSongDto.getCreatedAt())
                .createdBy(readSongDto.getCreatedBy())
                .creator(readSongDto.getCreator())
                .build();
    }

    @Override
    public ReadSongDto toDto(Song song) {
        return ReadSongDto.builder()
                .title(song.getTitle())
                .description(song.getDescription())
                .createdAt(song.getCreatedAt())
                .createdBy(song.getCreatedBy())
                .creator(song.getCreator())
                .build();
    }

    public List<ReadSongDto> toDtoList(List<Song> songs) {
        return songs.stream()
                .map(song -> new SongReadMapper().toDto(song))
                .toList();
    }
    public List<Song> toEntityList(List<ReadSongDto> dtoSongs) {
        return dtoSongs.stream()
                .map(song -> new SongReadMapper().toEntity(song))
                .toList();
    }
}
