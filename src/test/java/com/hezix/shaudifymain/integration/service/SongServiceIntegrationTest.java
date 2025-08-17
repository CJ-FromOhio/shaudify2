package com.hezix.shaudifymain.integration.service;

import com.hezix.shaudifymain.annotations.Integration;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.service.song.SongService;
import com.hezix.shaudifymain.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@Integration
public class SongServiceIntegrationTest {
    private static final Long SONG_ID = 1L;

    @Autowired
    private SongService songService;

    @Test
    void findById(){
        var expected = ReadSongDto.builder().id(SONG_ID).build();
        assertThat(songService.findSongById(SONG_ID)).isEqualTo(expected);
    }
}
