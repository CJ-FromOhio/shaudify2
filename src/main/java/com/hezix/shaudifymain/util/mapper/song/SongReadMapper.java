package com.hezix.shaudifymain.util.mapper.song;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.util.mapper.Mappable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SongReadMapper implements Mappable<Song, ReadSongDto> {

    @Override
    public Song toEntity(ReadSongDto readSongDto) {
        return Song.builder()
                .id(readSongDto.getId())
                .title(readSongDto.getTitle())
                .description(readSongDto.getDescription())
                .createdAt(readSongDto.getCreatedAt())
                .creator(User.builder()
                        .id(readSongDto.getCreatorId())
                        .build())
                .album(Album.builder()
                        .id(readSongDto.getAlbumId())
                        .build())
                .image(readSongDto.getImage())
                .songFile(readSongDto.getSong())
                .build();
    }

    @Override
    public ReadSongDto toDto(Song song) {
        return ReadSongDto.builder()
                .id(song.getId())
                .title(song.getTitle())
                .description(song.getDescription())
                .createdAt(song.getCreatedAt())
                .creatorId(song.getCreator().getId())
                .albumId(song.getAlbum() != null ? song.getAlbum().getId() : null)
                .image(song.getImage())
                .song(song.getSongFile())
                .build();
    }

    public List<ReadSongDto> toDtoList(List<Song> songs) {
        return Optional.ofNullable(songs)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toDto)
                .toList();
    }
    public Set<ReadSongDto> toDtoSet(Set<Song> songs) {
        return Optional.ofNullable(songs)
                .orElse(Collections.emptySet())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
    public List<Song> toEntityList(List<ReadSongDto> dtoSongs) {
        return Optional.ofNullable(dtoSongs)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toEntity)
                .toList();
    }
    public Set<Song> toEntitySet(Set<ReadSongDto> dtoSongs) {
        return Optional.ofNullable(dtoSongs)
                .orElse(Collections.emptySet())
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }
}
