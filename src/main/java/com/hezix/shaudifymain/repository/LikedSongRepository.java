package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikedSongRepository extends JpaRepository<LikedSong, Long> {
    Optional<LikedSong> findLikedSongByUserId(Long userId);
}
