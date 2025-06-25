package com.hezix.shaudifymain.mapper.songImage;

import com.hezix.shaudifymain.entity.song.SongFiles;
import com.hezix.shaudifymain.entity.song.dto.CreateSongFilesDto;
import com.hezix.shaudifymain.mapper.Mappable;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SongImageCreateMapper implements Mappable<SongFiles, CreateSongFilesDto> {
    @Override
    public SongFiles toEntity(CreateSongFilesDto createSongImageDto) {
        return SongFiles.builder()
                .imageFile(createSongImageDto.getImageFile())
                .songFile(createSongImageDto.getSongFile())
                .build();
    }

    @Override
    public CreateSongFilesDto toDto(SongFiles songImage) {
        return CreateSongFilesDto.builder()
                .imageFile(songImage.getImageFile())
                .songFile(songImage.getSongFile())
                .build();
    }


    public List<CreateSongFilesDto> toDtoList(List<SongFiles> t) {
        return List.of();
    }


    public List<SongFiles> toEntityList(List<CreateSongFilesDto> f) {
        return List.of();
    }
}
