package com.hezix.shaudifymain.mapper.album;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.albumSong.AlbumSong;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.mapper.Mapper;
import com.hezix.shaudifymain.service.AlbumSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class AlbumReadMapper implements Mapper<Album, ReadAlbumDto> {
    private final AlbumSongService albumSongService;
    @Override
    public Album toEntity(ReadAlbumDto readAlbumDto) {
        List<AlbumSong> list = readAlbumDto.getAlbumSongIds()
                .stream()
                .map(albumSongService::findBySongId);


        return Album.builder()
                .id(readAlbumDto.getId())
                .title(readAlbumDto.getTitle())
                .description(readAlbumDto.getDescription())
                .author(User
                        .builder()
                        .id(readAlbumDto.getAuthor_id())
                        .build())
                .albumSong(readAlbumDto.getAlbumSongIds())
                .build();
    }

    @Override
    public ReadAlbumDto toDto(Album album) {
        return null;
    }
}
