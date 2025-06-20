package com.hezix.shaudifymain.entity.song.form;

import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.CreateSongImageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSongFormDto {
    private CreateSongDto createSongDto;
    private CreateSongImageDto createSongImageDto;
}
