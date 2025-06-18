package com.hezix.shaudifymain.entity.song;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class SongImage {

    private MultipartFile file;
}
