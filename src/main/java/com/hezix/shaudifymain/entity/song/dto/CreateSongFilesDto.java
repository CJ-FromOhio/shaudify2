package com.hezix.shaudifymain.entity.song.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSongFilesDto {
    @NotNull(message = "image must be not null")
    private MultipartFile imageFile;
    @NotNull(message = "song must be not null")
    private MultipartFile songFile;
}
