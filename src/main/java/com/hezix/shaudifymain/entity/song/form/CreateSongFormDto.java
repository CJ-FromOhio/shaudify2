package com.hezix.shaudifymain.entity.song.form;

import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSongFormDto {
    private CreateSongDto createSongDto;
    private MultipartFile imageFile;
    private MultipartFile songFile;
}
