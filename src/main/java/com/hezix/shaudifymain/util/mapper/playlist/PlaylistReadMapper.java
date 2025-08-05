package com.hezix.shaudifymain.util.mapper.playlist;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.playlist.Playlist;
import com.hezix.shaudifymain.entity.playlist.dto.ReadPlaylistDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.util.mapper.Mappable;
import com.hezix.shaudifymain.util.mapper.song.SongReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlaylistReadMapper implements Mappable<Playlist, ReadPlaylistDto> {
    private final SongReadMapper songReadMapper;
    @Override
    public Playlist toEntity(ReadPlaylistDto readPlaylistDto) {
        return Playlist.builder()
                .id(readPlaylistDto.getId())
                .title(readPlaylistDto.getTitle())
                .type(readPlaylistDto.getType())
                .author(
                        User.builder()
                        .id(readPlaylistDto.getAuthor_id())
                        .build()
                )
                .songs(songReadMapper.toEntityList(readPlaylistDto.getSongs()))
                .image(readPlaylistDto.getImage())
                .created_at(readPlaylistDto.getCreated_at())
                .build();
    }

    @Override
    public ReadPlaylistDto toDto(Playlist playlist) {
        return ReadPlaylistDto.builder()
                .id(playlist.getId())
                .title(playlist.getTitle())
                .type(playlist.getType())
                .author_id(playlist.getAuthor().getId())
                .songs(songReadMapper.toDtoList(playlist.getSongs()))
                .image(playlist.getImage())
                .created_at(playlist.getCreated_at())
                .build();
    }
    @Override
    public List<ReadPlaylistDto> toDtoList(List<Playlist> playlists) {
        return playlists.stream()
                .map(this::toDto)
                .toList();
    }
    @Override
    public List<Playlist> toEntityList(List<ReadPlaylistDto> readPlaylistDtos) {
        return readPlaylistDtos.stream()
                .map(this::toEntity)
                .toList();
    }
    public Set<ReadPlaylistDto> toDtoSet(Set<Playlist> entity) {
        return Optional.ofNullable(entity)
                .orElse(Collections.emptySet())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
    public Set<Playlist> toEntitySet(Set<ReadPlaylistDto> dto) {
        return Optional.ofNullable(dto)
                .orElse(Collections.emptySet())
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }
}
