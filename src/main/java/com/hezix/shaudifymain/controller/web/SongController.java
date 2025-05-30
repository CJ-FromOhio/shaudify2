package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.service.SongService;
import com.hezix.shaudifymain.util.BindingResultParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/songs")
@RequiredArgsConstructor
@CustomControllerAdviceAnnotation
public class SongController {
    private final SongService songService;
    private final BindingResultParser bindingResultParser;

    @GetMapping()
    public String songs(Model model) {
        model.addAttribute("songs", songService.findAllSongs());
        return "songs/all_songs";
    }
    @GetMapping("/createSong")
    public String createSong(Model model) {
        model.addAttribute("createSongDto", new CreateSongDto());
        return "songs/create_song";
    }
    @PostMapping("/createSong")
    public String createSong(@Valid @ModelAttribute CreateSongDto createSongDto,
                             BindingResult bindingResult,
                             Model model,
                             @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResultParser.parseToString(bindingResult));
            return "songs/create_song";
        }
        Long id = songService.save(createSongDto, userDetails).getId();
        return "redirect:/songs/" + id;
    }
    @GetMapping("/{id}")
    public String song(@PathVariable Long id, Model model) {
        model.addAttribute("song", songService.findSongById(id));
        return "songs/song_by_id";
    }
}
