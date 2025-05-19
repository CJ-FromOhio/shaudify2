package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

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
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "songs/create_song";
        }
        Long id = songService.save(createSongDto, 1L).getId();
        return "redirect:/songs/" + id;
    }
    @GetMapping("/{id}")
    public String song(@PathVariable Long id, Model model) {
        model.addAttribute("song", songService.findSongById(id));
        return "songs/song_by_id";
    }
}
