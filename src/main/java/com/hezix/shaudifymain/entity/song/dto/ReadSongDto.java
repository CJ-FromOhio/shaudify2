package com.hezix.shaudifymain.entity.song.dto;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class ReadSongDto implements Serializable {

    private Long id;

    private String title;

    private String description;

    private Long creatorId;

    private Long albumId;

    private String image;
    private String song;

    private Instant createdAt;

}
