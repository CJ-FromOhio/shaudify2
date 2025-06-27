package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.album.dto.CreateAlbumDto;
import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.exception.OwnershipMismatchException;
import com.hezix.shaudifymain.mapper.album.AlbumCreateMapper;
import com.hezix.shaudifymain.mapper.album.AlbumReadMapper;
import com.hezix.shaudifymain.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final MinioImageService minioImageService;

    @Transactional(readOnly = true)
    public ReadAlbumDto findAlbumById(Long id) {
        return albumReadMapper.toDto(albumRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album not found by id: " + id)));
    }
    @Transactional(readOnly = true)
    public Album findAlbumEntityById(Long id) {
        return albumRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album Entity not found by id: " + id));
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
    @Transactional()
    public ReadAlbumDto addSongToAlbum(Long songId, Long albumId) {
        Album album = findAlbumEntityById(albumId);
        Song song = songService.findSongEntityById(songId);
        if (album.getAuthor() != song.getCreator()){
            throw new OwnershipMismatchException("Only the author of the album can add their own songs.");
        }
        album.getSongs().add(song);
        song.setAlbum(album);
        albumRepository.save(album);
        return albumReadMapper.toDto(album);
    }
    @Transactional(readOnly = true)
    public List<ReadAlbumDto> findAlbumsByAuthorId(Long authorId) {
        return albumReadMapper.toDtoList(albumRepository.findAlbumsByAuthorId(authorId));
    }
    @Transactional()
    public ReadAlbumDto uploadImage(Long id, MultipartFile files) {
        Album album = findAlbumEntityById(id);
        if(files == null || files.isEmpty()) {
            album.setImage("default_user_image.jpg");
            albumRepository.save(album);
            return albumReadMapper.toDto(album);
        }
        String fileName = minioImageService.upload(files);
        album.setImage(fileName);
        albumRepository.save(album);
        return albumReadMapper.toDto(album);
    }
}
