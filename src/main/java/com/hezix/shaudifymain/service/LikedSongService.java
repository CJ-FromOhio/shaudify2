package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.mapper.song.SongReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}
