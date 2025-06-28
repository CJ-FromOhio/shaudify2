package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>,
    FilterSongRepository{
    List<Song> findSongsByCreatorId(Long creatorId);
}
