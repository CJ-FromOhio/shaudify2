package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.albumSong.AlbumSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumSongRepository extends JpaRepository<AlbumSong, Long> {
    Optional<List<AlbumSong>> findByAlbumId(Long albumId);
    Optional<List<AlbumSong>> findBySongId(Long songId);
}
