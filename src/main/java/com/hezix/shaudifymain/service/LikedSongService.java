package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.mapper.song.SongReadMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikedSongService {

    private final UserService userService;
    private final SongService songService;
    private final SongReadMapper songReadMapper;

    @Transactional()
    public ReadSongDto like(Long songId, UserDetails userDetails) {
        User user = userService.findUserEntityByUsername(userDetails.getUsername());
        Song song = songService.findSongEntityById(songId);
        user.getLikedSongs().add(song);
        userService.update(user);
        return songReadMapper.toDto(song);
    }
    @Cacheable(value = "users:likedSong",
            key = "#userById")
    @Transactional(readOnly = true)
    public List<ReadSongDto> findLikedSongByUserId(Long userById) {
        return userService.findUserById(userById).getLikedSongs().stream().toList();
    }
}
