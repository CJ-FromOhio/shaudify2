package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.albumSong.AlbumSong;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.repository.AlbumSongRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumSongService {

    private final AlbumSongRepository albumSongRepository;

    @Transactional(readOnly = true)
    public List<AlbumSong> findByAlbumId(Long albumId) {
        return albumSongRepository.findByAlbumId(albumId).orElseThrow(() -> new EntityNotFoundException("Album Song not found by albumId: " + albumId));
    }

    @Transactional(readOnly = true)
    public List<AlbumSong> findBySongId(Long songId) {
        return albumSongRepository.findBySongId(songId).orElseThrow(() -> new EntityNotFoundException("Album Song not found by songId: " + songId));
    }
}
