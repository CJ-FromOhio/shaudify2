package com.hezix.shaudifymain.mapper.songImage;

import com.hezix.shaudifymain.entity.song.SongImage;
import com.hezix.shaudifymain.entity.song.dto.ReadSongImageDto;
import com.hezix.shaudifymain.mapper.Mappable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SongImageReadMapper implements Mappable<SongImage, ReadSongImageDto> {

    @Override
    public SongImage toEntity(ReadSongImageDto readSongImageDto) {
        return SongImage.builder()
                .file(readSongImageDto.getFile())
                .build();
    }

    @Override
    public ReadSongImageDto toDto(SongImage songImage) {
        return ReadSongImageDto.builder()
                .file(songImage.getFile())
                .build();
    }

    public List<ReadSongImageDto> toDtoList(List<SongImage> t) {
        return List.of();
    }

    public List<SongImage> toEntityList(List<ReadSongImageDto> f) {
        return List.of();
    }
}
