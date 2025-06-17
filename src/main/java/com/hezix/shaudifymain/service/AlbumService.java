package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.album.dto.CreateAlbumDto;
import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.mapper.album.AlbumCreateMapper;
import com.hezix.shaudifymain.mapper.album.AlbumReadMapper;
import com.hezix.shaudifymain.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumReadMapper albumReadMapper;
    private final UserService userService;
    private final SongService songService;
    private final AlbumCreateMapper albumCreateMapper;

    @Transactional(readOnly = true)
    public ReadAlbumDto findAlbumById(Long id) {
        return albumReadMapper.toDto(albumRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album not found by id: " + id)));
    }
    @Transactional(readOnly = true)
    public List<ReadAlbumDto> findAll() {
        return albumReadMapper.toDtoList(albumRepository.findAll());
    }
    @Transactional()
    public ReadAlbumDto save(CreateAlbumDto createAlbumDto, UserDetails userDetails) {
        Album album = albumCreateMapper.toEntity(createAlbumDto);
        User user = userService.findUserEntityByUsername(userDetails.getUsername());
        album.setAuthor(user);
        album.setCreatedAt(Instant.now());
        user.getAlbums().add(album);
        albumRepository.save(album);
        return albumReadMapper.toDto(album);
    }

    public ReadAlbumDto addSongToAlbum(Long songId, Long albumId) {
        ReadAlbumDto album = findAlbumById(albumId);
        ReadSongDto song = songService.findSongById(songId);
        album.getSongs().add(song);
        return album;
    }
    public List<ReadAlbumDto> findAlbumsByAuthorId(Long authorId) {
        return albumReadMapper.toDtoList(albumRepository.findAlbumsByAuthorId(authorId));
    }
}
