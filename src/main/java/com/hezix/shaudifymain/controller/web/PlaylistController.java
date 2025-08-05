package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.album.form.CreateAlbumFormDto;
import com.hezix.shaudifymain.entity.playlist.dto.CreatePlaylistDto;
import com.hezix.shaudifymain.entity.playlist.dto.ReadPlaylistDto;
import com.hezix.shaudifymain.entity.playlist.form.CreatePlaylistFormDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.web.PageResponse;
import com.hezix.shaudifymain.service.playlist.LikedPlaylistService;
import com.hezix.shaudifymain.service.playlist.PlaylistService;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.AuthPrincipalChecker;
import com.hezix.shaudifymain.util.BindingResultParser;
import com.hezix.shaudifymain.util.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.util.filter.AlbumFilter;
import com.hezix.shaudifymain.util.filter.PlaylistFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@CustomControllerAdviceAnnotation
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    private final UserService userService;
    private final BindingResultParser bindingResultParser;
    private final AuthPrincipalChecker authPrincipalChecker;

    @GetMapping()
    public String findAll(Model model, PlaylistFilter playlistFilter, Pageable pageable) {
        Page<ReadPlaylistDto> page = playlistService.findAllByFilter(playlistFilter, pageable);
        model.addAttribute("playlists", PageResponse.of(page));
        model.addAttribute("filter", playlistFilter);
        return "playlists/all_playlists";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        ReadPlaylistDto readPlaylistDto = playlistService.findPlaylistById(id);
        model.addAttribute("playlist", readPlaylistDto);
        return "playlists/playlist_by_id";
    }

    @GetMapping("/create")
    public String createPlaylist(Model model) {
        model.addAttribute("createPlaylistFormDto", new CreatePlaylistFormDto());
        return "playlists/create_playlist";
    }

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute CreatePlaylistFormDto createPlaylistFormDto,
                                 BindingResult bindingResult,
                                 Model model,
                                 @AuthenticationPrincipal Object principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResultParser.parseToString(bindingResult));
            return "playlists/create_playlist";
        }
        CreatePlaylistDto createPlaylistDto = createPlaylistFormDto.getCreatePlaylistDto();
        MultipartFile imageFile = createPlaylistFormDto.getImageFile();
        Long id = playlistService.save(createPlaylistDto, principal).getId();
        playlistService.uploadImage(id, imageFile);
        return "redirect:/playlists/" + id;
    }
}
