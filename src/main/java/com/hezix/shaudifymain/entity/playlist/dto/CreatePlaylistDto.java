package com.hezix.shaudifymain.entity.playlist.dto;

import com.hezix.shaudifymain.entity.playlist.PLAYLIST_TYPE;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlaylistDto {
    private Long id;

    private String title;
    @NotNull
    private PLAYLIST_TYPE  playlist_type;
}
