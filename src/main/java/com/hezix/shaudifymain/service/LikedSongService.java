package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.likedSong.dto.ReadLikedSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.mapper.likedSong.LikedSongReadMapper;
import com.hezix.shaudifymain.repository.LikedSongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikedSongService {

    private final UserService userService;
    private final SongService songService;
    private final LikedSongRepository likedSongRepository;
    private final LikedSongReadMapper likedSongReadMapper;

    @Transactional()
    public ReadLikedSongDto like(Long songId, UserDetails userDetails) {
        User user = userService.findUserEntityByUsername(userDetails.getUsername());
        if (findLikedSongBooleanByUserIdAndSongId(songId, userDetails)){
            return null;
        }
        var likedSong = LikedSong.builder()
                .song(songService.findSongEntityById(songId))
                .user(user)
                .build();

        likedSongRepository.save(likedSong);
        return mapEntityToRead(likedSong);

    }
    @Transactional(readOnly = true)
    public ReadLikedSongDto findLikedSongByUserIdAndSongId(Long songId, UserDetails userDetails) {
        User userEntityByUsername = userService.findUserEntityByUsername(userDetails.getUsername());
        LikedSong likedSong = likedSongRepository.findLikedSongByUserIdAndSongId(userEntityByUsername.getId(), songId)
                .orElse(null);
        return mapEntityToRead(likedSong);
    }
    @Transactional(readOnly = true)
    public Boolean findLikedSongBooleanByUserIdAndSongId(Long songId, UserDetails userDetails) {
        User user = userService.findUserEntityByUsername(userDetails.getUsername());
        boolean liked = likedSongRepository
                .findLikedSongByUserIdAndSongId(user.getId(), songId)
                .isPresent();
        return liked;
    }

    @Transactional(readOnly = true)
    public ReadLikedSongDto findLikedSongByUserId(Long userId) {
        return mapEntityToRead(likedSongRepository
                .findLikedSongByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Error finding liked song")));
    }
    @Transactional(readOnly = true)
    public ReadLikedSongDto findLikedSongById(Long id) {
        return mapEntityToRead(likedSongRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LikedSong with id " + id + " not found")));
    }
    @Transactional(readOnly = true)
    public List<ReadLikedSongDto> findAllLikedSongs() {
        return mapListEntityToListRead(likedSongRepository.findAll());
    }
    @Transactional()
    public ReadLikedSongDto deleteLikedSong(Long id) {
        return mapEntityToRead(likedSongRepository.deleteLikedSongById(id));
    }

    //mappers
    public LikedSong mapReadToEntity(ReadLikedSongDto likedSongDto) {
        return likedSongReadMapper.toEntity(likedSongDto);
    }

    public ReadLikedSongDto mapEntityToRead (LikedSong likedSong) {
        return likedSongReadMapper.toDto(likedSong);
    }
    public List<ReadLikedSongDto> mapListEntityToListRead(List<LikedSong> likedSongList) {
        return likedSongReadMapper.toDtoList(likedSongList);
    }
    public List<LikedSong> mapListReadToListEntity(List<ReadLikedSongDto> likedSongListDto) {
        return likedSongReadMapper.toEntityList(likedSongListDto);
    }
}
