package com.hezix.shaudifymain.entity.files;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ImageFile {
    private MultipartFile file;
}
