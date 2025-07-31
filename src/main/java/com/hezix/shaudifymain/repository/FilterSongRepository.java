package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.util.filter.SongFilter;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FilterSongRepository {
    List<Song> findAllByFilter(SongFilter songFilter);
}
