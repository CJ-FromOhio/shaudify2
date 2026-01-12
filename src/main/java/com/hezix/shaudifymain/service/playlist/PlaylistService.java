package com.hezix.shaudifymain.service.playlist;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.playlist.PLAYLIST_TYPE;
import com.hezix.shaudifymain.entity.playlist.Playlist;
import com.hezix.shaudifymain.entity.playlist.dto.CreatePlaylistDto;
import com.hezix.shaudifymain.entity.playlist.dto.ReadPlaylistDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.repository.PlaylistRepository;
import com.hezix.shaudifymain.service.minio.MinioImageService;
import com.hezix.shaudifymain.util.AuthPrincipalChecker;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.hezix.shaudifymain.util.filter.PlaylistFilter;
import com.hezix.shaudifymain.util.filter.QPredicates;
import com.hezix.shaudifymain.util.mapper.playlist.PlaylistCreateMapper;
import com.hezix.shaudifymain.util.mapper.playlist.PlaylistReadMapper;
import com.hezix.shaudifymain.util.mapper.user.UserReadMapper;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

import static com.hezix.shaudifymain.entity.playlist.QPlaylist.playlist;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final MinioImageService minioImageService;
    private final PlaylistCreateMapper playlistCreateMapper;
    private final PlaylistReadMapper playlistReadMapper;
    private final AuthPrincipalChecker authPrincipalChecker;
    private final MeterRegistry meterRegistry;

    @Timed(value = "service.playlist.save",
            description = "создание playlist",
            histogram = true)
    @Transactional()
    public ReadPlaylistDto save(CreatePlaylistDto createPlaylistDto, Object principal) {
        Playlist playlist = playlistCreateMapper.toEntity(createPlaylistDto);
        User user = authPrincipalChecker.check(principal);

        playlist.setAuthor(user);
        playlist.setCreated_at(Instant.now());
        user.getPlaylists().add(playlist);

        playlistRepository.save(playlist);
        return playlistReadMapper.toDto(playlist);
    }

    @Timed(value = "service.playlist.findAll",
            description = "поиск all",
            extraTags = {"method", "filter"})
    @Transactional(readOnly = true)
    public Page<ReadPlaylistDto> findAllByFilter(PlaylistFilter playlistFilter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(playlistFilter.getTitle(), playlist.title::containsIgnoreCase)
                .add(PLAYLIST_TYPE.PUBLIC, playlist.type::eq)
                .build();
        Page<ReadPlaylistDto> playlists = playlistRepository.findAll(predicate, pageable)
                .map(playlistReadMapper::toDto);
        return playlists;
    }

    @Timed(value = "service.playlist.uploadImage",
            description = "загрузка image")
    @Transactional()
    public ReadPlaylistDto uploadImage(Long id, MultipartFile files) {
        Playlist playlist = findEntityById(id);
        if (files == null || files.isEmpty()) {
            playlist.setImage("default_user_image.jpg");
            playlistRepository.save(playlist);
            return playlistReadMapper.toDto(playlist);
        }
        String fileName = minioImageService.upload(files);
        playlist.setImage(fileName);
        playlistRepository.save(playlist);
        return playlistReadMapper.toDto(playlist);
    }
    @Timed(value = "service.playlist.findById",
            description = "поиск по id",
            extraTags = {"method","entity"})
    @Transactional
    public Playlist findEntityById(Long id) {
        return playlistRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found by id: " + id));
    }

    @Transactional
    @Timed(value = "service.playlist.findById",
            description = "поиск по id",
            extraTags = {"method","dto"})
    public ReadPlaylistDto findPlaylistById(Long id) {
        return playlistReadMapper.toDto(findEntityById(id));
    }

    @PostConstruct
    public void initMetrics() {
        Gauge.builder("playlist_count", playlistRepository, (r) -> (double) r.count())
                .description("Количество всех playlist")
                .register(meterRegistry);
    }
}
