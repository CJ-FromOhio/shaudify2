package com.hezix.shaudifymain.service;


import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.filter.SongFilter;
import com.hezix.shaudifymain.mapper.song.SongCreateMapper;
import com.hezix.shaudifymain.mapper.song.SongReadMapper;

import com.hezix.shaudifymain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final UserService userService;
    private final SongCreateMapper songCreateMapper;
    private final SongReadMapper songReadMapper;
    private final MinioImageService minioImageService;

    private final MinioSongService minioSongService;

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
    public List<ReadSongDto> findSonsIdsByLikedSongList(Set<ReadSongDto> readLikedSongDtoList) {
        return readLikedSongDtoList.stream()
                .map(ReadSongDto::getId)
                .map(this::findSongById)
                .toList();
    }
    @Transactional(readOnly = true)
    public Song findSongEntityById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song Entity with id " + id + " not found"));
    }
    @Transactional(readOnly = true)
    public List<ReadSongDto> findAllSongsByFilter(SongFilter songFilter) {
        return mapListSongToListRead(songRepository.findAllByFilter(songFilter));
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
    public ReadSongDto uploadImage(Long id, MultipartFile files) {
        Song song = findSongEntityById(id);
        if(files == null || files.isEmpty()) {
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
        if(files == null || files.isEmpty()) {
            return songReadMapper.toDto(song);
        }
        String fileName = minioSongService.upload(files);
        song.setSongFile(fileName);
        songRepository.save(song);
        return songReadMapper.toDto(song);
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
