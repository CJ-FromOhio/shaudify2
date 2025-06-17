package com.hezix.shaudifymain.mapper.album;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.album.dto.CreateAlbumDto;
import com.hezix.shaudifymain.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class AlbumCreateMapper implements Mapper<Album, CreateAlbumDto> {
    @Override
    public Album toEntity(CreateAlbumDto createAlbumDto) {
        return Album.builder()
                .id(createAlbumDto.getId())
                .title(createAlbumDto.getTitle())
                .description(createAlbumDto.getDescription())
                .build();
    }

    @Override
    public CreateAlbumDto toDto(Album album) {
        return CreateAlbumDto.builder()
                .id(album.getId())
                .title(album.getTitle())
                .description(album.getDescription())
                .build();
    }
}
