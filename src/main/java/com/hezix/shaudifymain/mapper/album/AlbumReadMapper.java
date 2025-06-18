package com.hezix.shaudifymain.mapper.album;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.mapper.Mappable;
import com.hezix.shaudifymain.mapper.song.SongReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlbumReadMapper implements Mappable<Album, ReadAlbumDto> {
    private final SongReadMapper songReadMapper;
    @Override
    public Album toEntity(ReadAlbumDto readAlbumDto) {
        return Album.builder()
                .id(readAlbumDto.getId())
                .title(readAlbumDto.getTitle())
                .description(readAlbumDto.getDescription())
                .author(User
                        .builder()
                        .id(readAlbumDto.getAuthor_id())
                        .build())
                .songs(songReadMapper.toEntityList(readAlbumDto.getSongs()))
                .build();
    }

    @Override
    public ReadAlbumDto toDto(Album album) {
        return ReadAlbumDto.builder()
                .id(album.getId())
                .title(album.getTitle())
                .description(album.getDescription())
                .author_id(album.getAuthor().getId())
                .songs(songReadMapper.toDtoList(album.getSongs()))
                .build();
    }
    public List<ReadAlbumDto> toDtoList(List<Album> albums) {
        return Optional.ofNullable(albums)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<Album> toEntityList(List<ReadAlbumDto> readAlbumDtos) {
        return Optional.ofNullable(readAlbumDtos)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toEntity)
                .toList();
    }
}
