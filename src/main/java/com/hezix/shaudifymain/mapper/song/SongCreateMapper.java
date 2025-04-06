package com.hezix.shaudifymain.mapper.song;

import com.hezix.shaudifymain.entity.Song;
import com.hezix.shaudifymain.entity.dto.song.CreateSongDto;
import com.hezix.shaudifymain.mapper.Mapper;

import java.time.Instant;

public class SongCreateMapper implements Mapper<Song, CreateSongDto> {
    @Override
    public Song toEntity(CreateSongDto createSongDto) {
        return Song.builder()
                .title(createSongDto.getTitle())
                .description(createSongDto.getDescription())
                .creator(createSongDto.getCreator())
                .createdAt(Instant.now())
                .build();
    }

    @Override
    public CreateSongDto toDto(Song song) {
        return CreateSongDto.builder()
                .title(song.getTitle())
                .description(song.getDescription())
                .creator(song.getCreator())
                .build();
    }
}
