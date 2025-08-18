package com.hezix.shaudifymain.integration.service;

import com.hezix.shaudifymain.integration.IntegrationTestBase;
import com.hezix.shaudifymain.service.song.SongService;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class SongServiceIntegrationTest extends IntegrationTestBase {
    private static final Long SONG_ID = 1L;

    @Autowired
    private SongService songService;

    @Test
    @Sql("classpath:sql/data.sql")
    void findById_throwIfNotExists(){
        assertThrows(EntityNotFoundException.class, () -> songService.findSongById(SONG_ID));
    }
}
