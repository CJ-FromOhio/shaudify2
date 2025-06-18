package com.hezix.shaudifymain.mapper.likedSong;

import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.likedSong.dto.ReadLikedSongDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.mapper.Mappable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikedSongReadMapper implements Mappable<LikedSong, ReadLikedSongDto> {

    @Override
    public LikedSong toEntity(ReadLikedSongDto readLikedSongDto) {
        return LikedSong.builder()
                .id(readLikedSongDto.getId())
                .song(Song.builder()
                        .id(readLikedSongDto.getSongId())
                        .build())
                .user(User.builder()
                        .id(readLikedSongDto.getUserId())
                        .build())
                .build();
    }

    @Override
    public ReadLikedSongDto toDto(LikedSong likedSong) {
        return ReadLikedSongDto.builder()
                .id(likedSong.getId())
                .songId(likedSong.getSong().getId())
                .userId(likedSong.getUser().getId())
                .build();
    }

    public List<ReadLikedSongDto> toDtoList(List<LikedSong> likedSongs) {
        return Optional.ofNullable(likedSongs)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<LikedSong> toEntityList(List<ReadLikedSongDto> likedSongDtos) {
        return Optional.ofNullable(likedSongDtos)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::toEntity)
                .toList();
    }
}
