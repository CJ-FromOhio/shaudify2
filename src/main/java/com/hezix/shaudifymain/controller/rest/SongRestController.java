package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.util.annotations.CustomRestControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.service.song.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/songs")
@RequiredArgsConstructor
@Tag(name = "Songs Rest Controller")
@CustomRestControllerAdviceAnnotation
public class SongRestController {
    private final SongService songService;

    @PostMapping("/create")
    @Operation(
            summary = "добавление песни",
            description = "в параметрах передаем идентификатор пользователя который создает песню, и саму песню"
    )
    public ResponseEntity<ReadSongDto> createSong(@Valid @RequestBody CreateSongDto createSongDto,
                                                  BindingResult bindingResult,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok().body(songService.save(createSongDto, userDetails));
        }
    }
    @GetMapping("/")
    @Operation(
            summary = "получаем все песни"
    )
    public ResponseEntity<List<ReadSongDto>> getAllSongs() {
        return ResponseEntity.ok().body(songService.findAllSongs());
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "получаем песни по айди"
    )
    public ResponseEntity<ReadSongDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(songService.findSongById(id));
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "удаляем песню по айди"
    )
    public ResponseEntity<ReadSongDto> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(songService.deleteSongById(id));
    }
    @GetMapping("/random")
    public ResponseEntity<ReadSongDto> randomSong() {
        return ResponseEntity.ok().body(songService.getRandomSong());
    }
    @PostMapping("/{id}/image")
    private ResponseEntity<ReadSongDto> uploadImage(@PathVariable Long id,
                                 @Validated @ModelAttribute MultipartFile file) {
        ReadSongDto readSongDto = songService.uploadImage(id, file);
        return ResponseEntity.ok().body(readSongDto);
    }
}
