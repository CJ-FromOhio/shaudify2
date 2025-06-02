package com.hezix.shaudifymain.entity.likedSong.dto;

import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadLikedSongDto {

    private Long id;

    private Long userId;

    private Long songId;
}
