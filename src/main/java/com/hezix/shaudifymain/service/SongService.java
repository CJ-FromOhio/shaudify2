package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.likedSong.dto.ReadLikedSongDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.SongImage;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.CreateSongImageDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.mapper.song.SongCreateMapper;
import com.hezix.shaudifymain.mapper.song.SongReadMapper;
import com.hezix.shaudifymain.mapper.songImage.SongImageCreateMapper;
import com.hezix.shaudifymain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final UserService userService;
    private final SongCreateMapper songCreateMapper;
    private final SongReadMapper songReadMapper;
    private final ImageService imageService;
    private final SongImageCreateMapper songImageCreateMapper;

    @Transactional()
    public ReadSongDto save(CreateSongDto createSongDto, UserDetails userDetails) {
        Song song = mapCreateToSong(createSongDto);
        song.setCreatedAt(Instant.now());
        var user = userService.findUserEntityByUsername(userDetails.getUsername());
        user.getCreatedSong().add(song);
        song.setCreator(user);
        songRepository.save(song);
        return mapSongToRead(song);
    }
    public List<ReadSongDto> findSongsByCreatorId(Long creatorId) {
        return songReadMapper.toDtoList(songRepository
                .findSongsByCreatorId(creatorId));
    }
    @Transactional(readOnly = true)
    public ReadSongDto findSongById(Long id) {
        return mapSongToRead(songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song with id " + id + " not found")));
    }
    @Transactional(readOnly = true)
    public List<ReadSongDto> findSonsIdsByLikedSongList(List<ReadLikedSongDto> readLikedSongDtoList) {
        return readLikedSongDtoList.stream()
                .map(ReadLikedSongDto::getSongId)
                .map(this::findSongById)
                .toList();
    }
    @Transactional(readOnly = true)
    public Song findSongEntityById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song Entity with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<ReadSongDto> findAllSongs() {
        return mapListSongToListRead(songRepository.findAll());
    }
    @Transactional()
    public ReadSongDto deleteSongById(Long id) {
        var song = findSongById(id);
        songRepository.delete(mapReadToSong(song));
        return song;
    }
    @Transactional
    public void uploadImage(Long id, CreateSongImageDto dto) {
        Song song = findSongEntityById(id);
        SongImage image = songImageCreateMapper.toEntity(dto);
        String fileName = imageService.upload(image);
        if(song.getImages() == null || song.getImages().isEmpty()) {
            song.setImages(new ArrayList<>());
        }
        song.getImages().add(fileName);
        songRepository.save(song);
    }

    //mappers
    public ReadSongDto mapSongToRead(Song song) {
        return songReadMapper.toDto(song);
    }
    public CreateSongDto mapSongToCreate(Song song) {
        return songCreateMapper.toDto(song);
    }
    public Song mapReadToSong(ReadSongDto song) {
        return songReadMapper.toEntity(song);
    }
    public Song mapCreateToSong(CreateSongDto song) {
        return songCreateMapper.toEntity(song);
    }
    public List<ReadSongDto> mapListSongToListRead(List<Song> songList) {
        return songReadMapper.toDtoList(songList);
    }
    public List<Song> mapListReadToListSong(List<ReadSongDto> songList) {
        return songReadMapper.toEntityList(songList);
    }


}
