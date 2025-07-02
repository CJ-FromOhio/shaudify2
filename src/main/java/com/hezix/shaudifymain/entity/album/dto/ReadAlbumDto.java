package com.hezix.shaudifymain.entity.album.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"songs"})
public class ReadAlbumDto implements Serializable {
    private Long id;

    private String title;

    private String description;
    @JsonBackReference
    private Long author_id;

    private String image;
    @JsonManagedReference
    private List<ReadSongDto> songs;
}
