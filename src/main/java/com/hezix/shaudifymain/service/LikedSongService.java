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
//        if (findLikedSongBooleanByUserIdAndSongId(songId, userDetails)){
//
//        }
        user.getLikedSongs().add(song);
        userService.update(user);
        return songReadMapper.toDto(song);
    }
    @Transactional(readOnly = true)
    public Song findLikedSongById(Long id) {
//        return likedSongReadMapper.toDto(likedSongRepository
//                .findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("LikedSong with id " + id + " not found")));
        return null;
    }
    @Transactional(readOnly = true)
    public List<ReadSongDto> findAllLikedSongs() {
//        return likedSongReadMapper.toDtoList(likedSongRepository.findAll());
        return null;
    }
    @Transactional()
    public ReadSongDto deleteLikedSong(Long id) {
//        return likedSongReadMapper.toDto(likedSongRepository.deleteLikedSongById(id));
        return null;
    }

}
