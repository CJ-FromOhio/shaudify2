package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.util.annotations.CustomRestControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.service.song.LikedSongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/song/likes")
@Tag(name = "Liked song Rest Controller")
@CustomRestControllerAdviceAnnotation
public class LikedSongRestController {
    private final LikedSongService likedSongService;

    @PostMapping("{id}/like")
    public ResponseEntity<ReadSongDto> like(@PathVariable Long id,
                       @AuthenticationPrincipal Object principal) {
        ReadSongDto dto = likedSongService.like(id, principal);
        return ResponseEntity.ok().body(dto);
    }
    @PostMapping("{id}/dislike")
    public ResponseEntity<ReadSongDto> dislike(@PathVariable Long id,
                          @AuthenticationPrincipal Object principal) {
        ReadSongDto dto = likedSongService.dislike(id, principal);
        return ResponseEntity.ok().body(dto);
    }
}
