package com.hezix.shaudifymain.entity.album.form;

import com.hezix.shaudifymain.entity.album.dto.CreateAlbumDto;
import com.hezix.shaudifymain.entity.files.ImageFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAlbumFormDto {
    private CreateAlbumDto createAlbumDto;
    private MultipartFile imageFile;
}
