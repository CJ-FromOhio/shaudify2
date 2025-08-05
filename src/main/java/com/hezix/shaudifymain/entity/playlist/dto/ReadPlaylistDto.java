package com.hezix.shaudifymain.entity.playlist.dto;

import com.hezix.shaudifymain.entity.playlist.PLAYLIST_TYPE;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class ReadPlaylistDto implements Serializable {
    private Long id;

    private String title;

    private PLAYLIST_TYPE type;

    private Long author_id;

    private String image;
    private Boolean isLiked;

    private List<ReadSongDto> songs;

    private Instant created_at;
}
