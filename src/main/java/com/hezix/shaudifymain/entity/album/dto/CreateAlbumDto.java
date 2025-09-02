package com.hezix.shaudifymain.entity.album.dto;

import com.hezix.shaudifymain.entity.album.ALBUM_TYPE;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAlbumDto {
    private Long id;

    private String title;
    private String description;
    @NotNull
    private ALBUM_TYPE albumType;
}
