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
    private final UserReadMapper userReadMapper;
    private final AuthPrincipalChecker authPrincipalChecker;

    @Transactional()
    public ReadPlaylistDto save(CreatePlaylistDto createPlaylistDto, Object principal) {
        Playlist playlist =  playlistCreateMapper.toEntity(createPlaylistDto);
        User user = authPrincipalChecker.check(principal);

        playlist.setAuthor(user);
        playlist.setCreated_at(Instant.now());
        user.getPlaylists().add(playlist);

        playlistRepository.save(playlist);
        return playlistReadMapper.toDto(playlist);
    }

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
    public Playlist findEntityById(Long id) {
        return playlistRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found by id: " + id));
    }
    @Transactional()
    public ReadPlaylistDto uploadImage(Long id, MultipartFile files) {
        Playlist playlist = findEntityById(id);
        if(files == null || files.isEmpty()) {
            playlist.setImage("default_user_image.jpg");
            playlistRepository.save(playlist);
            return playlistReadMapper.toDto(playlist);
        }
        String fileName = minioImageService.upload(files);
        playlist.setImage(fileName);
        playlistRepository.save(playlist);
        return playlistReadMapper.toDto(playlist);
    }

    public ReadPlaylistDto findPlaylistById(Long id) {
        return playlistReadMapper.toDto(findEntityById(id));
    }
}
