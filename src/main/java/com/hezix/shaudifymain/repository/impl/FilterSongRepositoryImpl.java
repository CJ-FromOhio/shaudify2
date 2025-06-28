package com.hezix.shaudifymain.repository.impl;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.filter.QPredicates;
import com.hezix.shaudifymain.filter.SongFilter;
import com.hezix.shaudifymain.repository.FilterSongRepository;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hezix.shaudifymain.entity.song.QSong.song;


@Repository
@RequiredArgsConstructor
public class FilterSongRepositoryImpl implements FilterSongRepository {
    private final EntityManager em;
    @Override
    public List<Song> findAllByFilter(SongFilter songFilter) {
        var predicate = QPredicates.builder()
                .add(songFilter.title(), song.title::containsIgnoreCase)
                .build();

        return new JPAQuery<Song>(em)
                .select(song)
                .from(song)
                .where(predicate)
                .fetch();
    }
}
