package com.hezix.shaudifymain.entity.playlist.form;

import com.hezix.shaudifymain.entity.playlist.dto.CreatePlaylistDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlaylistFormDto {
    private CreatePlaylistDto createPlaylistDto;
    private MultipartFile imageFile;
}
