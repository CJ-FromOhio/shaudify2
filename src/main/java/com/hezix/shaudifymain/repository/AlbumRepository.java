package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Long>,
        FilterAlbumRepository{
    List<Album> findAlbumsByAuthorId(Long id);
}
