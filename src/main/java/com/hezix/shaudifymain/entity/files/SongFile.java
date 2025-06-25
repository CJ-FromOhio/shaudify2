package com.hezix.shaudifymain.entity.files;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class SongFile {
    private MultipartFile file;
}
