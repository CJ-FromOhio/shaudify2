package com.hezix.shaudifymain.controller.web;


import com.hezix.shaudifymain.entity.album.ALBUM_TYPE;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.util.AuthPrincipalChecker;
import com.hezix.shaudifymain.util.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.album.form.CreateAlbumFormDto;
import com.hezix.shaudifymain.entity.web.PageResponse;
import com.hezix.shaudifymain.util.filter.AlbumFilter;
import com.hezix.shaudifymain.service.album.AlbumService;
import com.hezix.shaudifymain.service.song.SongService;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.BindingResultParser;
import com.hezix.shaudifymain.util.mapper.user.UserReadMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final UserReadMapper userReadMapper;
    private final BindingResultParser bindingResultParser;
    private final AuthPrincipalChecker authPrincipalChecker;


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
        model.addAttribute("types", ALBUM_TYPE.values());
        return "albums/create_album";
    }
    @PostMapping("/saveAlbum")
    public String saveAlbum(@Valid @ModelAttribute CreateAlbumFormDto createAlbumFormDto,
                             BindingResult bindingResult,
                             Model model,
                             @AuthenticationPrincipal Object principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResultParser.parseToString(bindingResult));
            return "albums/create_album";
        }

        var createAlbumDto = createAlbumFormDto.getCreateAlbumDto();
        var imageFile = createAlbumFormDto.getImageFile();
        Long id = albumService.save(createAlbumDto, principal).getId();
        albumService.uploadImage(id, imageFile);
        return "redirect:/albums/" + id;
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id,
                           Model model) {
        ReadAlbumDto album = albumService.findAlbumById(id);
        model.addAttribute("user", userService.findUserById(album.getAuthor_id()));
        model.addAttribute("album", album);
        return "albums/album_by_id";
    }
    @GetMapping("/add/{songId}")
    public String addSongToAlbum(@PathVariable Long songId,
                                 Model model,
                                 @AuthenticationPrincipal Object principal) {
        ReadUserDto user = userReadMapper.toDto(authPrincipalChecker.check(principal));
        model.addAttribute("song", songService.findSongById(songId));
        model.addAttribute("albums", user.getAlbums());
        return "albums/add_song_to_album";
    }

    @PostMapping("/add/{songId}/{albumId}")
    public String addSongToAlbum(@PathVariable Long songId,
                                 @PathVariable Long albumId,
                                 @AuthenticationPrincipal Object principal) {
        albumService.addSongToAlbum(songId, albumId, principal);
        return "redirect:/albums/" + albumId;
    }
}
