package com.hezix.shaudifymain.integration.service;

import com.hezix.shaudifymain.annotations.Integration;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.integration.IntegrationTestBase;
import com.hezix.shaudifymain.integration.RedisSingletonContainer;
import com.hezix.shaudifymain.repository.SongRepository;
import com.hezix.shaudifymain.service.song.SongService;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.redis.testcontainers.RedisContainer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Integration
@Testcontainers
public class SongServiceIntegrationTest {
    private static final Long SONG_ID = 1001L;
    private static final Long SONG_NON_EXISTENT_ID = 1L;
    @Autowired
    private SongService songService;
    @Autowired
    private SongRepository songRepository;
    @Container
    private static PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:17-alpine")
            .waitingFor(Wait.forListeningPort())
            .waitingFor(Wait.forLogMessage(".*database system is ready.*", 1));
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }
    @Test
    void findById_throwIfNotExists(){
        assertThrows(EntityNotFoundException.class,
                () -> songService.findSongById(SONG_NON_EXISTENT_ID));
    }
    @Test
    void findById(){
        var expected = ReadSongDto.builder().id(SONG_ID).build();
        var actual = songService.findSongById(SONG_ID);
        System.out.println(actual);
        assertThat(actual).isEqualTo(expected);
    }
}
