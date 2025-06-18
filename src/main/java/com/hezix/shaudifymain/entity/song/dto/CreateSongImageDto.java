package com.hezix.shaudifymain.entity.song.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class CreateSongImageDto {
    @NotNull(message = "image must be not null")
    private MultipartFile file;
}
