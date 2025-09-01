package com.hezix.shaudifymain.util.mapper.song;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.util.mapper.Mappable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class SongCreateMapper implements Mappable<Song, CreateSongDto> {
    @Override
    public Song toEntity(CreateSongDto createSongDto) {
        return Song.builder()
                .id(createSongDto.getId())
                .title(createSongDto.getTitle())
                .description(createSongDto.getDescription())
                .build();
    }

    @Override
    public CreateSongDto toDto(Song song) {
        return CreateSongDto.builder()
                .id(song.getId())
                .title(song.getTitle())
                .description(song.getDescription())
                .build();
    }

    @Override
    public List<CreateSongDto> toDtoList(List<Song> t) {
        return List.of();
    }

    @Override
    public List<Song> toEntityList(List<CreateSongDto> f) {
        return List.of();
    }
}
