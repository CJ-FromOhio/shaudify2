package com.hezix.shaudifymain.repository.impl;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.album.QAlbum;
import com.hezix.shaudifymain.filter.AlbumFilter;
import com.hezix.shaudifymain.filter.QPredicates;
import com.hezix.shaudifymain.repository.FilterAlbumRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hezix.shaudifymain.entity.album.QAlbum.*;

@Repository
@RequiredArgsConstructor
public class FilterAlbumRepositoryImpl implements FilterAlbumRepository {
    private final EntityManager em;

    @Override
    public List<Album> findAllByFilter(AlbumFilter albumFilter) {
        var predicate = QPredicates.builder()
                .add(albumFilter.title(), album.title::containsIgnoreCase)
                .build();
        return new JPAQuery<Album>(em)
                .from(album)
                .select(album)
                .where(predicate)
                .fetch();
    }
}
