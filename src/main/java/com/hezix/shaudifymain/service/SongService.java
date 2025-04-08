package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.mapper.song.SongCreateMapper;
import com.hezix.shaudifymain.mapper.song.SongReadMapper;
import com.hezix.shaudifymain.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final UserService userService;
    private final SongCreateMapper songCreateMapper;
    private final SongReadMapper songReadMapper;

    @Transactional()
    public ReadSongDto save(CreateSongDto createSongDto, Long userId) {
        Song song = mapCreateToSong(createSongDto);
        song.setCreatedAt(Instant.now());
        var user = userService.findUserEntityById(userId);
        user.getCreatedSong().add(song);
        song.setCreatedBy(user.getUsername());
        song.setCreator(user);
        songRepository.save(song);
        return mapSongToRead(song);
    }
    @Transactional(readOnly = true)
    public ReadSongDto findSongById(Long id) {
        return mapSongToRead(songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song with id " + id + " not found")));
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

    //mappers
    public ReadSongDto mapSongToRead(Song song) {
        return songReadMapper.toDto(song);
    }
    public CreateSongDto mapSongToCreate(Song song) {
        return songCreateMapper.toDto(song);
    }
    public List<ReadSongDto> mapListSongToListRead(List<Song> songList) {
        return songReadMapper.toDtoList(songList);
    }
    public Song mapReadToSong(ReadSongDto song) {
        return songReadMapper.toEntity(song);
    }
    public Song mapCreateToSong(CreateSongDto song) {
        return songCreateMapper.toEntity(song);
    }
    public List<Song> mapListReadToListSong(List<ReadSongDto> songList) {
        return songReadMapper.toEntityList(songList);
    }
}
