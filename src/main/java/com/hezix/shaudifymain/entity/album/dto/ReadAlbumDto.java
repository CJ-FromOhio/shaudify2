package com.hezix.shaudifymain.entity.album.dto;

import com.hezix.shaudifymain.entity.album.ALBUM_TYPE;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadAlbumDto implements Serializable {
    private Long id;

    private String title;

    private String description;

    private Long author_id;

    private ALBUM_TYPE album_type;

    private String image;

    private List<ReadSongDto> songs;

    private Instant created_at;
}
