package com.hezix.shaudifymain.util.filter;

import com.hezix.shaudifymain.entity.album.ALBUM_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AlbumFilter {
    private String title = "";
    private ALBUM_TYPE albumType;
}
