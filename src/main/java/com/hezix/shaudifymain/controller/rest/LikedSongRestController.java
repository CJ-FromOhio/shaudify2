package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.annotations.CustomRestControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.service.LikedSongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<ReadSongDto> like(@PathVariable("songId") Long songId,
                                            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(likedSongService.like(songId, userDetails));
    }
}
