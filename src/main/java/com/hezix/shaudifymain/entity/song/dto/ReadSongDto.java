package com.hezix.shaudifymain.entity.song.dto;

import com.hezix.shaudifymain.entity.user.User;
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
