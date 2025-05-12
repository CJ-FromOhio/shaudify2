package com.hezix.shaudifymain.mapper.likedSong;

import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.likedSong.dto.ReadLikedSongDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
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

    private final SongReadMapper songReadMapper;
    private final UserReadMapper userReadMapper;

    @Override
    public LikedSong toEntity(ReadLikedSongDto readLikedSongDto) {
        return LikedSong.builder()
                .id(readLikedSongDto.getId())
                .song(songReadMapper.toEntity(readLikedSongDto.getSong()))
                .user(userReadMapper.toEntity(readLikedSongDto.getUser()))
                .build();
    }

    @Override
    public ReadLikedSongDto toDto(LikedSong likedSong) {
        return ReadLikedSongDto.builder()
                .id(likedSong.getId())
                .song(songReadMapper.toDto(likedSong.getSong()))
                .user(userReadMapper.toDto(likedSong.getUser()))
                .build();
    }

    public List<ReadLikedSongDto> toDtoList(List<LikedSong> likedSongs) {
        return likedSongs.stream()
                .map(likedSong -> new LikedSongReadMapper(songReadMapper,userReadMapper).toDto(likedSong))
                .toList();
    }

    public List<LikedSong> toEntityList(List<ReadLikedSongDto> likedSongDtos) {
        return likedSongDtos.stream()
                .map(likedSongDto -> new LikedSongReadMapper(songReadMapper,userReadMapper).toEntity(likedSongDto))
                .toList();
    }
}
