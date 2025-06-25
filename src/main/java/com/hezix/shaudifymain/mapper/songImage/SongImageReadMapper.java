package com.hezix.shaudifymain.mapper.songImage;

import com.hezix.shaudifymain.entity.song.SongFiles;
import com.hezix.shaudifymain.entity.song.dto.ReadSongFilesDto;
import com.hezix.shaudifymain.mapper.Mappable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SongImageReadMapper implements Mappable<SongFiles, ReadSongFilesDto> {

    @Override
    public SongFiles toEntity(ReadSongFilesDto readSongImageDto) {
        return SongFiles.builder()
                .imageFile(readSongImageDto.getImageFile())
                .songFile(readSongImageDto.getSongFile())
                .build();
    }

    @Override
    public ReadSongFilesDto toDto(SongFiles songImage) {
        return ReadSongFilesDto.builder()
                .imageFile(songImage.getImageFile())
                .songFile(songImage.getSongFile())
                .build();
    }

    public List<ReadSongFilesDto> toDtoList(List<SongFiles> t) {
        return List.of();
    }

    public List<SongFiles> toEntityList(List<ReadSongFilesDto> f) {
        return List.of();
    }
}
