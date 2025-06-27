package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.song.form.CreateSongFormDto;

import com.hezix.shaudifymain.service.AlbumService;
import com.hezix.shaudifymain.service.MinioImageService;
import com.hezix.shaudifymain.service.SongService;
import com.hezix.shaudifymain.util.BindingResultParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/songs")
@RequiredArgsConstructor
@CustomControllerAdviceAnnotation
public class SongController {
    private final SongService songService;
    private final MinioImageService minioImageService;
    private final AlbumService albumService;
    private final BindingResultParser bindingResultParser;

    @GetMapping()
    public String songs(Model model) {
        model.addAttribute("songs", songService.findAllSongs());
        return "songs/all_songs";
    }
    @GetMapping("/createSong")
    public String createSong(Model model) {
        model.addAttribute("createSongFormDto", new CreateSongFormDto());
        return "songs/create_song";
    }
    @PostMapping("/createSong")
    public String createSong(@Valid @ModelAttribute CreateSongFormDto createSongFormDto,
                             BindingResult bindingResult,
                             Model model,
                             @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResultParser.parseToString(bindingResult));
            return "songs/create_song";
        }
        var createSongDto = createSongFormDto.getCreateSongDto();
        var songFile = createSongFormDto.getSongFile();
        var imageFile = createSongFormDto.getImageFile();
        Long id = songService.save(createSongDto, userDetails).getId();
        songService.uploadImage(id, imageFile);
        songService.uploadSong(id, songFile);
        return "redirect:/songs/" + id;
    }
    @GetMapping("/{id}")
    public String song(@PathVariable Long id, Model model) {
        ReadSongDto song = songService.findSongById(id);
        model.addAttribute("song", song);
        if (song.getAlbumId() != null) {
            model.addAttribute("song_album", albumService.findAlbumById(song.getAlbumId()));
        } else {
            model.addAttribute("song_album", null); // или можно показать "Без альбома"
        }
        return "songs/song_by_id";
    }
    @PostMapping("/{id}/image")
    private void uploadSongImage(@PathVariable Long id,
                                 @Validated @ModelAttribute MultipartFile file) {
        songService.uploadImage(id, file);
    }
}
