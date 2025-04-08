package com.hezix.shaudifymain.entity.song.dto;

import com.hezix.shaudifymain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSongDto {

    private String title;

    private String description;

    private User creator;
}
