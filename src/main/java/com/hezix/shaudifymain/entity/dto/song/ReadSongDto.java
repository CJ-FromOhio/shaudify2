package com.hezix.shaudifymain.entity.dto.song;

import com.hezix.shaudifymain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadSongDto {

    private String title;

    private String description;

    private User creator;

    private Instant createdAt;

    private String createdBy;

}
