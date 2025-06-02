package com.hezix.shaudifymain.mapper.likedSong;

import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.likedSong.dto.ReadLikedSongDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.mapper.Mapper;
import com.hezix.shaudifymain.mapper.song.SongReadMapper;
import com.hezix.shaudifymain.mapper.user.UserReadMapper;
import com.hezix.shaudifymain.service.LikedSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LikedSongReadMapper implements Mapper<LikedSong, ReadLikedSongDto> {

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
        return likedSongs.stream()
                .map(this::toDto)
                .toList();
    }

    public List<LikedSong> toEntityList(List<ReadLikedSongDto> likedSongDtos) {
        return likedSongDtos.stream()
                .map(this::toEntity)
                .toList();
    }
}
