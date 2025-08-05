package com.hezix.shaudifymain.entity.playlist.dto;

import com.hezix.shaudifymain.entity.playlist.PLAYLIST_TYPE;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadPlaylistDto {
    private Long id;

    private String title;

    private PLAYLIST_TYPE type;

    private Long author_id;

    private String image;

    private List<ReadSongDto> songs;

    private Instant created_at;
}
