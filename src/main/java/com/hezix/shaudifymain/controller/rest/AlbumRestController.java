package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.album.dto.CreateAlbumDto;
import com.hezix.shaudifymain.entity.album.dto.ReadAlbumDto;
import com.hezix.shaudifymain.entity.album.form.CreateAlbumFormDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.web.PageResponse;
import com.hezix.shaudifymain.service.album.AlbumService;
import com.hezix.shaudifymain.util.BindingResultParser;
import com.hezix.shaudifymain.util.annotations.CustomRestControllerAdviceAnnotation;
import com.hezix.shaudifymain.util.filter.AlbumFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/albums")
@RequiredArgsConstructor
@CustomRestControllerAdviceAnnotation
public class AlbumRestController {
    private final AlbumService albumService;
    private final BindingResultParser bindingResultParser;
    @GetMapping()
    public ResponseEntity<PageResponse<ReadAlbumDto>> findAllAlbums(AlbumFilter albumFilter, Pageable pageable) {
        Page<ReadAlbumDto> page = albumService.findAllByFilter(albumFilter, pageable);
        return ResponseEntity.ok().body(PageResponse.of(page));
    }
    @PostMapping("/createAlbum")
    public ResponseEntity<?> createAlbum(@Valid @RequestBody CreateAlbumDto createAlbumDto,
                                                    BindingResult bindingResult,
                                                    @AuthenticationPrincipal Object principal) {
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResultParser.parseToString(bindingResult));
        }
        ReadAlbumDto dto = albumService.save(createAlbumDto, principal);
        return ResponseEntity.ok().body(dto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReadAlbumDto> findById(@PathVariable Long id) {
        ReadAlbumDto album = albumService.findAlbumById(id);
        return ResponseEntity.ok().body(album);
    }
    @PostMapping("/add/{songId}/{albumId}")
    public ResponseEntity<ReadAlbumDto> addSongToAlbum(@PathVariable Long songId,
                                   @PathVariable Long albumId,
                                   @AuthenticationPrincipal Object principal) {
        ReadAlbumDto readAlbumDto = albumService.addSongToAlbum(songId, albumId, principal);
        return ResponseEntity.ok().body(readAlbumDto);
    }
    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<ReadAlbumDto> uploadImage(@PathVariable Long id,
                                 @RequestBody MultipartFile image) {
        ReadAlbumDto readAlbumDto = albumService.uploadImage(id, image);
        return ResponseEntity.ok().body(readAlbumDto);
    }
}
