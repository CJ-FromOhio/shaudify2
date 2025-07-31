package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.util.filter.AlbumFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilterAlbumRepository {
    List<Album> findAllByFilter(AlbumFilter albumFilter);
}
