package com.hezix.shaudifymain.mapper.song;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.time.Instant;
@Component
public class SongCreateMapper implements Mapper<Song, CreateSongDto> {
    @Override
    public Song toEntity(CreateSongDto createSongDto) {
        return Song.builder()
                .id(createSongDto.getId())
                .title(createSongDto.getTitle())
                .description(createSongDto.getDescription())
                .creator(User.builder()
                        .id(1L)
                        .build())
                .createdAt(Instant.now())
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
}
