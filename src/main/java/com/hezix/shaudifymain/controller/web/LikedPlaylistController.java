package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.service.playlist.LikedPlaylistService;
import com.hezix.shaudifymain.util.annotations.CustomControllerAdviceAnnotation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/playlists")
@RequiredArgsConstructor
@Controller
@CustomControllerAdviceAnnotation
public class LikedPlaylistController {
    private final LikedPlaylistService likedPlaylistService;
    @PostMapping("{id}/like")
    public String like(@PathVariable Long id, @AuthenticationPrincipal Object principal) {
        likedPlaylistService.like(id, principal);
        return "redirect:/playlists/" + id;
    }
    @PostMapping("{id}/dislike")
    public String dislike(@PathVariable Long id, @AuthenticationPrincipal Object principal) {
        likedPlaylistService.dislike(id, principal);
        return "redirect:/playlists/" + id;
    }
}
