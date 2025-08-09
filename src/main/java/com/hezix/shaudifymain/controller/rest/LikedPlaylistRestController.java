package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.entity.playlist.dto.ReadPlaylistDto;
import com.hezix.shaudifymain.service.playlist.LikedPlaylistService;
import com.hezix.shaudifymain.util.annotations.CustomRestControllerAdviceAnnotation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/playlists/likes")
@RequiredArgsConstructor
@CustomRestControllerAdviceAnnotation
public class LikedPlaylistRestController {
    private final LikedPlaylistService likedPlaylistService;
    @PostMapping("{id}/like")
    public ResponseEntity<ReadPlaylistDto> like(@PathVariable Long id, @AuthenticationPrincipal Object principal) {
        ReadPlaylistDto dto = likedPlaylistService.like(id, principal);
        return ResponseEntity.ok().body(dto);
    }
    @PostMapping("{id}/dislike")
    public ResponseEntity<ReadPlaylistDto> dislike(@PathVariable Long id, @AuthenticationPrincipal Object principal) {
        ReadPlaylistDto dto = likedPlaylistService.dislike(id, principal);
        return ResponseEntity.ok().body(dto);
    }
}
