package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String createSong(@ModelAttribute CreateSongDto createSongDto,
                             Model model) {
        songService.save(createSongDto, 1L);
        return "songs/all_songs";
    }
    @GetMapping("/{id}")
    public String song(@PathVariable Long id, Model model) {
        model.addAttribute("song", songService.findSongById(id));
        return "songs/song_by_id";
    }
}
