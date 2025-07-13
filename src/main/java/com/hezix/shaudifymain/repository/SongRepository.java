package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>,
        QuerydslPredicateExecutor<Song> {
    List<Song> findSongsByCreatorId(Long creatorId);

}
