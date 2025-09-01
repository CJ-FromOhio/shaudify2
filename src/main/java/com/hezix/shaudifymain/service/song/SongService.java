package com.hezix.shaudifymain.service.song;


import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.service.minio.MinioImageService;
import com.hezix.shaudifymain.service.minio.MinioSongService;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.AuthPrincipalChecker;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.hezix.shaudifymain.util.filter.QPredicates;
import com.hezix.shaudifymain.util.filter.SongFilter;
import com.hezix.shaudifymain.util.mapper.song.SongCreateMapper;
import com.hezix.shaudifymain.util.mapper.song.SongReadMapper;

import com.hezix.shaudifymain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.hezix.shaudifymain.entity.song.QSong.song;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final SongCreateMapper songCreateMapper;
    private final SongReadMapper songReadMapper;
    private final MinioImageService minioImageService;
    private final AuthPrincipalChecker authPrincipalChecker;
    private final MinioSongService minioSongService;
    private final Random random;

    @Caching(evict = {
            @CacheEvict(value = "songs:all", allEntries = true),
            @CacheEvict(value = "songs:id", allEntries = true),
            @CacheEvict(value = "songs:likedSong:id", allEntries = true),
    })
    @Transactional()
    public ReadSongDto save(CreateSongDto createSongDto, Object principal) {
        Song song = songCreateMapper.toEntity(createSongDto);
        song.setCreatedAt(Instant.now());
        if(principal != null) {
            User user = authPrincipalChecker.check(principal);
            user.getCreatedSongs().add(song);
            song.setCreator(user);
        }
        songRepository.save(song);
        return songReadMapper.toDto(song);
    }
    @Transactional(readOnly = true)
    public ReadSongDto getRandomSong() {
        List<Song> songs = songRepository.findAll();
        if (songs.isEmpty()) {
            throw new EntityNotFoundException("No songs found in the database");
        }
        int randomIndex = random.nextInt(songs.size());
        return songReadMapper.toDto(songs.get(randomIndex));
    }
    @Transactional(readOnly = true)
    public List<ReadSongDto> findSongsByCreatorId(Long creatorId) {
        return songReadMapper.toDtoList(songRepository
                .findSongsByCreatorId(creatorId));
    }

    @Cacheable(
            value = "songs:id",
            key = "#id"
    )
    @Transactional(readOnly = true)
    public ReadSongDto findSongById(Long id) {
        return songReadMapper.toDto(songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song with id " + id + " not found")));
    }

    @Cacheable(
            value = "songs:likedSong:id",
            key = "#readLikedSongDtoList.hashCode()"
    )
    @Transactional(readOnly = true)
    public List<ReadSongDto> findSongsIdsByLikedSongList(Set<Long> readLikedSongDtoList) {
        return readLikedSongDtoList.stream()
                .map(this::findSongById)
                .toList();
    }

    @Transactional(readOnly = true)
    public Song findSongEntityById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song Entity with id " + id + " not found"));
    }

    @Cacheable(
            value = "songs:all",
            key = "#songFilter.hashCode() + '_' + #pageable.pageNumber + '_' + #pageable.pageSize"
    )
    @Transactional(readOnly = true)
    public Page<ReadSongDto> findAllSongsByFilter(SongFilter songFilter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(songFilter.getTitle(), song.title::containsIgnoreCase)
                .build();

        return songRepository.findAll(predicate, pageable)
                .map(songReadMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ReadSongDto> findAllSongs() {
        return songReadMapper.toDtoList(songRepository.findAll());
    }

    @Transactional()
    public ReadSongDto deleteSongById(Long id) {
        var song = findSongById(id);
        songRepository.delete(songReadMapper.toEntity(song));
        return song;
    }

    @Transactional
    public ReadSongDto uploadImage(Long id, MultipartFile files) {
        Song song = findSongEntityById(id);
        if (files == null || files.isEmpty()) {
            song.setImage("default_song_image.jpg");
            songRepository.save(song);
            return songReadMapper.toDto(song);
        }
        String fileName = minioImageService.upload(files);
        song.setImage(fileName);
        songRepository.save(song);
        return songReadMapper.toDto(song);
    }

    @Transactional
    public ReadSongDto uploadSong(Long id, MultipartFile files) {
        Song song = findSongEntityById(id);
        if (files == null || files.isEmpty()) {
            return songReadMapper.toDto(song);
        }
        String fileName = minioSongService.upload(files);
        song.setSongFile(fileName);
        songRepository.save(song);
        return songReadMapper.toDto(song);
    }


}
