package com.hezix.shaudifymain.service.album;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.album.dto.CreateAlbumDto;
import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.user.Role;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.service.minio.MinioImageService;
import com.hezix.shaudifymain.service.song.SongService;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.AuthPrincipalChecker;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.hezix.shaudifymain.util.exception.OwnershipMismatchException;
import com.hezix.shaudifymain.util.filter.AlbumFilter;
import com.hezix.shaudifymain.util.filter.QPredicates;
import com.hezix.shaudifymain.util.mapper.album.AlbumCreateMapper;
import com.hezix.shaudifymain.util.mapper.album.AlbumReadMapper;
import com.hezix.shaudifymain.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

import static com.hezix.shaudifymain.entity.album.QAlbum.album;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumReadMapper albumReadMapper;

    private final SongService songService;
    private final AlbumCreateMapper albumCreateMapper;
    private final MinioImageService minioImageService;
    private final AuthPrincipalChecker authPrincipalChecker;
    @Cacheable(
            value = "album:id",
            key = "#id"
    )
    @Transactional(readOnly = true)
    public ReadAlbumDto findAlbumById(Long id) {
        return albumReadMapper.toDto(findAlbumEntityById(id));
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
    @Cacheable(
            value = "album:all",
            key = "#albumFilter.hashCode() + '_' + #pageable.pageNumber + '_' + #pageable.pageSize"
    )
    @Transactional(readOnly = true)
    public Page<ReadAlbumDto> findAllByFilter(AlbumFilter albumFilter, Pageable pageable) {
        var predicate = QPredicates.builder()
                    .add(albumFilter.getTitle(), album.title::containsIgnoreCase)
                    .build();
        return albumRepository.findAll(predicate, pageable)
                .map(albumReadMapper::toDto);
    }
    @Caching(evict = {
            @CacheEvict(value = "album:all", allEntries = true),
            @CacheEvict(value = "album:id", allEntries = true),
    })
    @Transactional()
    public ReadAlbumDto save(CreateAlbumDto createAlbumDto, Object principal) {
        Album album = albumCreateMapper.toEntity(createAlbumDto);
        User user = authPrincipalChecker.check(principal);
        album.setAuthor(user);
        album.setCreatedAt(Instant.now());
        user.getAlbums().add(album);
        albumRepository.save(album);
        return albumReadMapper.toDto(album);
    }
    @Transactional()
    public ReadAlbumDto addSongToAlbum(Long songId, Long albumId, Object principal) {
        User user = authPrincipalChecker.check(principal);
//        if (user.getRole().equals(Role.AUTHOR)) {}
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
    public List<ReadAlbumDto> findAlbumsByAuthor(Object principal) {
        User user = authPrincipalChecker.check(principal);
        return albumReadMapper.toDtoList(albumRepository.findAlbumsByAuthorId(user.getId()));
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
