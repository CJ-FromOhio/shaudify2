package com.hezix.shaudifymain.entity.album.dto;

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
}
