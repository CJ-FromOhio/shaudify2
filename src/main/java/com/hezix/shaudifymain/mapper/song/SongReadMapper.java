package com.hezix.shaudifymain.mapper.song;

import com.hezix.shaudifymain.entity.Song;
import com.hezix.shaudifymain.entity.dto.song.ReadSongDto;
import com.hezix.shaudifymain.mapper.Mapper;

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
}
