package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.playlist.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long>,
        QuerydslPredicateExecutor<Playlist> {
}
