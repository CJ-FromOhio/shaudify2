package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Long>,
        QuerydslPredicateExecutor<Album> {
    List<Album> findAlbumsByAuthorId(Long id);
}
