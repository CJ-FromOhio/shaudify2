package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/songs")
@RequiredArgsConstructor
public class SongRestController {
    private final SongService songService;

    @PostMapping("/{id}")
    public ResponseEntity<ReadSongDto> createSong(@PathVariable("id") Long userId, @Valid @RequestBody CreateSongDto createSongDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        else {
            return ResponseEntity.ok().body(songService.save(createSongDto, userId));
        }
    }
    @GetMapping("/")
    public ResponseEntity<List<ReadSongDto>> getAllSongs() {
        return ResponseEntity.ok().body(songService.findAllSongs());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReadSongDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(songService.findSongById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ReadSongDto> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(songService.deleteSongById(id));
    }
}
