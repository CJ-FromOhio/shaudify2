package com.hezix.shaudifymain.entity.song.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ReadSongImageDto {

    private MultipartFile file;
}
