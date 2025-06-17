package com.hezix.shaudifymain.controller.web;


import com.hezix.shaudifymain.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.album.dto.CreateAlbumDto;
import com.hezix.shaudifymain.service.AlbumService;
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
}
