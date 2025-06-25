package com.hezix.shaudifymain.mapper.songImage;

import com.hezix.shaudifymain.entity.song.SongImage;
import com.hezix.shaudifymain.entity.song.dto.CreateSongImageDto;
import com.hezix.shaudifymain.mapper.Mappable;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SongImageCreateMapper implements Mappable<SongImage, CreateSongImageDto> {
    @Override
    public SongImage toEntity(CreateSongImageDto createSongImageDto) {
        return SongImage.builder()
                .file(createSongImageDto.getFile())
                .build();
    }

    @Override
    public CreateSongImageDto toDto(SongImage songImage) {
        return CreateSongImageDto.builder()
                .file(songImage.getFile())
                .build();
    }


    public List<CreateSongImageDto> toDtoList(List<SongImage> t) {
        return List.of();
    }


    public List<SongImage> toEntityList(List<CreateSongImageDto> f) {
        return List.of();
    }
}
