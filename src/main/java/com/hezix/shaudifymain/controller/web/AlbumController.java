package com.hezix.shaudifymain.controller.web;


import com.hezix.shaudifymain.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.album.dto.CreateAlbumDto;
import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.service.AlbumService;
import com.hezix.shaudifymain.service.SongService;
import com.hezix.shaudifymain.service.UserService;
import com.hezix.shaudifymain.util.BindingResultParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Controller
@RequestMapping("/albums")
@CustomControllerAdviceAnnotation
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;
    private final SongService songService;
    private final UserService userService;
    private final BindingResultParser bindingResultParser;

    @GetMapping()
    public String findAllAlbums(Model model){
        model.addAttribute("albums", albumService.findAll());
        return "albums/all_albums";
    }

    @GetMapping("/createAlbum")
    public String createAlbum(Model model) {
        model.addAttribute("createAlbumDto", new CreateAlbumDto());
        return "albums/create_album";
    }
    @PostMapping("/saveAlbum")
    public String saveAlbum(@Valid @ModelAttribute CreateAlbumDto createAlbumDto,
                             BindingResult bindingResult,
                             Model model,
                             @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResultParser.parseToString(bindingResult));
            return "albums/create_album";
        }
        Long id = albumService.save(createAlbumDto, userDetails).getId();
        return "redirect:/albums/" + id;
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("album", albumService.findAlbumById(id));
        return "albums/album_by_id";
    }
    @GetMapping("/{songId}/add")
    public String addSongToAlbum(@PathVariable Long songId,
                                 Model model,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        ReadUserDto user = userService.findUserByUsername(userDetails.getUsername());
        model.addAttribute("albums", albumService.findAlbumsByAuthorId(user.getId()));
        model.addAttribute("song", songService.findSongById(songId));
        return "albums/add_song_to_album";
    }
    @PostMapping("/{songId}/add")
    public String addSongToAlbum(@ModelAttribute ReadAlbumDto readAlbumDto,
                                 BindingResult bindingResult,
                                 @PathVariable Long songId,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResultParser.parseToString(bindingResult));
            return "albums/add_song_to_album";
        }
        Long id = albumService.addSongToAlbum(songId, readAlbumDto.getId()).getId();
        return "redirect:/albums/" + id;
    }
}
