package com.hezix.shaudifymain.entity.song.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadSongDto implements Serializable {

    private Long id;

    private String title;

    private String description;
    @JsonBackReference
    private Long creatorId;
    @JsonBackReference
    private Long albumId;

    private String image;
    private String song;

    private Instant createdAt;
}
