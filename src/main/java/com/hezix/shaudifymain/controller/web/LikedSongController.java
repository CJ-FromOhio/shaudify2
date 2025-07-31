package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.util.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.service.song.LikedSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@CustomControllerAdviceAnnotation
@RequestMapping("/songs")
public class LikedSongController {
    private final LikedSongService likedSongService;

    @PostMapping("{id}/like")
    public String like(@PathVariable Long id,
                       @AuthenticationPrincipal UserDetails userDetails) {
        likedSongService.like(id,userDetails);
        return "redirect:/songs";
    }
}
