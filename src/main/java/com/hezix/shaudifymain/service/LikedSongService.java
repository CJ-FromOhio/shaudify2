package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.likedSong.dto.ReadLikedSongDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.mapper.likedSong.LikedSongReadMapper;
import com.hezix.shaudifymain.repository.LikedSongRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikedSongService {

    private final UserService userService;
    private final SongService songService;
    private final LikedSongRepository likedSongRepository;
    private final LikedSongReadMapper likedSongReadMapper;

    @Transactional()
    public ReadLikedSongDto like(Long songId, Long userId) {
        var likedSong = LikedSong.builder()
                .song(songService.findSongEntityById(songId))
                .user(userService.findUserEntityById(userId))
                .build();
        likedSongRepository.save(likedSong);
        return likedSongReadMapper.toDto(likedSong);
    }

    @Transactional(readOnly = true)
    public LikedSong findLikedSongByUserId(Long userId) {
        return likedSongRepository.findLikedSongByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Error finding liked song"));
    }
    @Transactional(readOnly = true)
    public LikedSong findLikedSongById(Long id) {
        return likedSongRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("LikedSong with id " + id + " not found"));
    }
    @Transactional(readOnly = true)
    public List<LikedSong> findAllLikedSongs() {
        return likedSongRepository.findAll();
    }
    public LikedSong deleteLikedSong(Long id) {
        return likedSongRepository.deleteLikedSongById(id);
    }
}
