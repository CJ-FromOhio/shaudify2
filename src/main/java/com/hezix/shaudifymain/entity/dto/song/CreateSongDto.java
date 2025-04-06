package com.hezix.shaudifymain.entity.dto.song;

import com.hezix.shaudifymain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSongDto {

    private String title;

    private String description;

    private User creator;


}
