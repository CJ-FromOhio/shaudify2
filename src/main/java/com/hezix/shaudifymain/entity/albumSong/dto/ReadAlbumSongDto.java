package com.hezix.shaudifymain.entity.albumSong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadAlbumSongDto {
    private Long id;

    private Long song_id;
    private Long album_id;
}
