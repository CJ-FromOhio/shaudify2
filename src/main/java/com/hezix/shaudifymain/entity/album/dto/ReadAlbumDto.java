package com.hezix.shaudifymain.entity.album.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadAlbumDto {
    private Long id;

    private String title;

    private String description;

    private Long author_id;

    private List<Long> albumSongIds;
}
