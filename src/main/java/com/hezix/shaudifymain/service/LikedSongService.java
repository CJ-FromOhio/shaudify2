package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.repository.LikedSongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikedSongService {

    private final LikedSongRepository likedSongRepository;

    @Transactional()
    public LikedSong save(Song song, User user) {
        var likedSong = LikedSong.builder()
                .song(song)
                .user(user)
                .build();
        return likedSongRepository.save(likedSong);
    }
    public LikedSong findLikedSongByUserId(Long userId) {
        return likedSongRepository.findLikedSongByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Error finding liked song"));
    }

    public LikedSong findLikedSongById(Long id) {
        return likedSongRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("LikedSong with id " + id + " not found"));
    }

    public List<LikedSong> findAllLikedSongs() {
        return likedSongRepository.findAll();
    }
}
