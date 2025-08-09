package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.entity.playlist.dto.CreatePlaylistDto;
import com.hezix.shaudifymain.entity.playlist.dto.ReadPlaylistDto;
import com.hezix.shaudifymain.entity.playlist.form.CreatePlaylistFormDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.web.PageResponse;
import com.hezix.shaudifymain.service.playlist.PlaylistService;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.BindingResultParser;
import com.hezix.shaudifymain.util.annotations.CustomRestControllerAdviceAnnotation;
import com.hezix.shaudifymain.util.filter.PlaylistFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/playlists")
@RequiredArgsConstructor
@CustomRestControllerAdviceAnnotation
public class PlaylistRestController {
    private final PlaylistService playlistService;
    private final BindingResultParser bindingResultParser;

    @GetMapping()
    public ResponseEntity<PageResponse<ReadPlaylistDto>> findAll(PlaylistFilter playlistFilter, Pageable pageable) {
        Page<ReadPlaylistDto> page = playlistService.findAllByFilter(playlistFilter, pageable);
        PageResponse<ReadPlaylistDto> pageResponse = PageResponse.of(page);
        return ResponseEntity.ok().body(pageResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReadPlaylistDto> findById(@PathVariable Long id) {
        ReadPlaylistDto readPlaylistDto = playlistService.findPlaylistById(id);
        return ResponseEntity.ok().body(readPlaylistDto);
    }
    @PostMapping("/create")
    public ResponseEntity<?> save(@Valid @ModelAttribute CreatePlaylistDto createPlaylistDto,
                       BindingResult bindingResult,
                       @AuthenticationPrincipal Object principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResultParser.parseToString(bindingResult));
        }
        ReadPlaylistDto dto = playlistService.save(createPlaylistDto, principal);
        return ResponseEntity.ok().body(dto);
    }
    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable Long id,
                                         @RequestBody MultipartFile image){
        ReadPlaylistDto playlistDto = playlistService.uploadImage(id, image);
        return ResponseEntity.ok().body(playlistDto);
    }
}
