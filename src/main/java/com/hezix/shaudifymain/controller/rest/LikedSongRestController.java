package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.annotations.CustomRestControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.likedSong.dto.ReadLikedSongDto;
import com.hezix.shaudifymain.service.LikedSongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/likes")
@Tag(name = "Liked song Rest Controller")
@CustomRestControllerAdviceAnnotation
public class LikedSongRestController {
    private final LikedSongService likedSongService;

    @PostMapping("{songId}/{userId}")
    @Operation(
            summary = "добавление (лайкаем)",
            description = "в параметры передаём идентификатор песни, идентификатор пользователя"
    )
    public ResponseEntity<ReadLikedSongDto> like(@PathVariable("songId") Long songId,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(likedSongService.like(songId, userDetails));
    }

    @GetMapping("/")
    @Operation(
            summary = "получение всех лайков всех пользователей"
    )
    public ResponseEntity<List<ReadLikedSongDto>> getAllLikedSong() {
        return ResponseEntity.ok(likedSongService.findAllLikedSongs());
    }

    @GetMapping("{id}")
    @Operation(
            summary = "получение всех лайков определенного пользователя"
    )
    public ResponseEntity<ReadLikedSongDto> getById(@PathVariable("id") Long likedSongId) {
        return ResponseEntity.ok(likedSongService.findLikedSongById(likedSongId));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "удаляем (дизлайкаем)"
    )
    public ResponseEntity<ReadLikedSongDto> delete(@PathVariable("id") Long likedSongId) {
        return ResponseEntity.ok(likedSongService.deleteLikedSong(likedSongId));
    }
}
