package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.likedSong.dto.ReadLikedSongDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.mapper.likedSong.LikedSongReadMapper;
import com.hezix.shaudifymain.repository.LikedSongRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikedSongService {

    private final UserService userService;
    private final SongService songService;
    private final LikedSongRepository likedSongRepository;
    private final LikedSongReadMapper likedSongReadMapper;
    HashMap<Long,Long> likedSongMap = new HashMap<>();

    @Transactional()
    public ReadLikedSongDto like(Long songId, UserDetails userDetails) {
        User user = userService.findUserEntityByUsername(userDetails.getUsername());
        var likedSong = LikedSong.builder()
                .song(songService.findSongEntityById(songId))
                .user(userService.findUserEntityById(user.getId()))
                .build();


        if (likedSongMap.containsKey(likedSong.getUser().getId())) {
            if (likedSongMap.containsValue(likedSong.getSong().getId())) {
                return mapEntityToRead(likedSong);
            }
        }

        likedSongMap.put(likedSong.getUser().getId(), likedSong.getSong().getId());
        likedSongRepository.save(likedSong);
        return mapEntityToRead(likedSong);

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
