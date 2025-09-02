package com.hezix.shaudifymain.util.filter;

import com.hezix.shaudifymain.entity.playlist.PLAYLIST_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PlaylistFilter {
    private String title;
    private PLAYLIST_TYPE type;
}
