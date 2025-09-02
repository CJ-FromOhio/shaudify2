package com.hezix.shaudifymain.util.mapper.playlist;

import com.hezix.shaudifymain.entity.playlist.Playlist;
import com.hezix.shaudifymain.entity.playlist.dto.CreatePlaylistDto;
import com.hezix.shaudifymain.util.mapper.Mappable;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaylistCreateMapper implements Mappable<Playlist, CreatePlaylistDto> {
    @Override
    public Playlist toEntity(CreatePlaylistDto createPlaylistDto) {
        return Playlist.builder()
                .title(createPlaylistDto.getTitle())
                .type(createPlaylistDto.getPlaylist_type())
                .build();
    }

    @Override
    public List<Playlist> toEntityList(List<CreatePlaylistDto> createPlaylistDtos) {
        return createPlaylistDtos.stream()
                .map(this::toEntity)
                .toList();
    }

    @Override
    public CreatePlaylistDto toDto(Playlist playlist) {
        return CreatePlaylistDto.builder()
                .title(playlist.getTitle())
                .build();
    }

    @Override
    public List<CreatePlaylistDto> toDtoList(List<Playlist> playlists) {
        return playlists.stream()
                .map(this::toDto)
                .toList();
    }
}
