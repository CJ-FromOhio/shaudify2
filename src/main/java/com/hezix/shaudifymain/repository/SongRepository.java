package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>,
        QuerydslPredicateExecutor<Song> {
    @Timed(value = "repository.song.findByAuthorId",
            description = "find song by author Id")
    List<Song> findSongsByCreatorId(Long creatorId);
}
