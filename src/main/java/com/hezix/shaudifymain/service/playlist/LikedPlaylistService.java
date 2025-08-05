package com.hezix.shaudifymain.service.playlist;

import com.hezix.shaudifymain.entity.playlist.Playlist;
import com.hezix.shaudifymain.entity.playlist.dto.ReadPlaylistDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.AuthPrincipalChecker;
import com.hezix.shaudifymain.util.mapper.playlist.PlaylistReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikedPlaylistService {

    private final UserService userService;
    private final PlaylistService playlistService;
    private final PlaylistReadMapper playlistReadMapper;
    private final AuthPrincipalChecker authPrincipalChecker;
    @CacheEvict(
            value = "users:LikedPlaylist",
            allEntries = true
    )
    @Transactional()
    public ReadPlaylistDto like(Long playlistId, Object principal) {
        User user = authPrincipalChecker.check(principal);
        Playlist playlist = playlistService.findEntityById(playlistId);
        user.getLikedPlaylists().add(playlist);
        userService.update(user);
        return playlistReadMapper.toDto(playlist);
    }

    @Transactional(readOnly = true)
    public Set<Long> findLikedPlaylistIdsByUser(Long userById) {
        return userService.findUserById(userById).getLikedPlaylists().stream().map(playlist -> playlist.getId()).collect(Collectors.toSet());
    }
    @CacheEvict(
            value = "users:LikedPlaylist",
            allEntries = true
    )
    @Transactional()
    public ReadPlaylistDto dislike(Long playlistId, Object principal) {
        User user = authPrincipalChecker.check(principal);
        Playlist playlist = playlistService.findEntityById(playlistId);
        user.getLikedPlaylists().remove(playlist);
        userService.update(user);
        return playlistReadMapper.toDto(playlist);
    }
}
