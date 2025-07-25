package com.hezix.shaudifymain.controller.web;


import com.hezix.shaudifymain.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.album.dto.CreateAlbumDto;
import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.album.form.CreateAlbumFormDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.entity.web.PageResponse;
import com.hezix.shaudifymain.filter.AlbumFilter;
import com.hezix.shaudifymain.service.AlbumService;
import com.hezix.shaudifymain.service.SongService;
import com.hezix.shaudifymain.service.UserService;
import com.hezix.shaudifymain.util.BindingResultParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public String findAllAlbums(Model model, AlbumFilter albumFilter, Pageable pageable) {
        Page<ReadAlbumDto> page = albumService.findAllByFilter(albumFilter, pageable);

        model.addAttribute("albums", PageResponse.of(page));
        model.addAttribute("filter", albumFilter);

        return "albums/all_albums";
    }

    @GetMapping("/createAlbum")
    public String createAlbum(Model model) {
        model.addAttribute("createAlbumFormDto", new CreateAlbumFormDto());
        return "albums/create_album";
    }
    @PostMapping("/saveAlbum")
    public String saveAlbum(@Valid @ModelAttribute CreateAlbumFormDto createAlbumFormDto,
                             BindingResult bindingResult,
                             Model model,
                             @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResultParser.parseToString(bindingResult));
            return "albums/create_album";
        }
        var createAlbumDto = createAlbumFormDto.getCreateAlbumDto();
        var imageFile = createAlbumFormDto.getImageFile();
        Long id = albumService.save(createAlbumDto, userDetails).getId();
        albumService.uploadImage(id, imageFile);
        return "redirect:/albums/" + id;
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("album", albumService.findAlbumById(id));
        return "albums/album_by_id";
    }
    @GetMapping("/add/{songId}")
    public String addSongToAlbum(@PathVariable Long songId,
                                 Model model,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        ReadUserDto user = userService.findUserByUsername(userDetails.getUsername());
        model.addAttribute("albums", albumService.findAlbumsByAuthorId(user.getId()));
        model.addAttribute("song", songService.findSongById(songId));
        return "albums/add_song_to_album";
    }
    @PostMapping("/add/{songId}")
    public String addSongToAlbum(@RequestParam Long albumId,
                                 @PathVariable Long songId,
                                 Model model) {
        albumService.addSongToAlbum(songId, albumId);
        return "redirect:/albums/" + albumId;
    }
}
