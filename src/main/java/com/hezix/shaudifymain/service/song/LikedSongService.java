package com.hezix.shaudifymain.service.song;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.AuthPrincipalChecker;
import com.hezix.shaudifymain.util.mapper.song.SongReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikedSongService {

    private final UserService userService;
    private final SongService songService;
    private final SongReadMapper songReadMapper;
    private final AuthPrincipalChecker authPrincipalChecker;
    @CacheEvict(
            value = "users:likedSong",
            allEntries = true
    )
    @Transactional()
    public ReadSongDto like(Long songId, Object principal) {
        User user = authPrincipalChecker.check(principal);
        Song song = songService.findSongEntityById(songId);
        user.getLikedSongs().add(song);
        userService.update(user);
        return songReadMapper.toDto(song);
    }

    @Cacheable(value = "users:likedSong",
            key = "#userById")
    @Transactional(readOnly = true)
    public List<ReadSongDto> findLikedSongByUserId(Long userById) {
        return userService.findUserById(userById).getLikedSongs().stream().toList();
    }
    @CacheEvict(
            value = "users:likedSong",
            allEntries = true
    )
    @Transactional()
    public ReadSongDto unlike(Long songId, Object principal) {
        User user = authPrincipalChecker.check(principal);
        Song song = songService.findSongEntityById(songId);
        user.getLikedSongs().remove(song);
        userService.update(user);
        return songReadMapper.toDto(song);
    }
}
