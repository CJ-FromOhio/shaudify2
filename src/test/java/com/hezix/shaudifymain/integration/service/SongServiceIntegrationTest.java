package com.hezix.shaudifymain.integration.service;

import com.hezix.shaudifymain.annotations.Integration;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.service.song.SongService;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.hezix.shaudifymain.util.filter.SongFilter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Integration
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SongServiceIntegrationTest {
    private static final Long SONG_ID = 1L;
    private static final Long CREATOR_ID = 1L;
    private static final Long SONG_NON_EXISTENT_ID = 9999L;
    private static final String TITLE_FOR_FILTER = "";
    @Autowired
    private SongService songService;

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
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    @Order(1)
    void findAll(){
        int expected = 15;
        var actual = songService.findAllSongs();
        assertThat(actual).hasSize(expected);
    }
    @Test
    @Order(2)
    void findAll_byFilter(){
        int expected = 15;
        Pageable pageable = PageRequest.of(0, 15);
        var actual = songService.findAllSongsByFilter(new SongFilter(TITLE_FOR_FILTER), pageable);
        assertThat(actual).hasSize(expected);
    }
    @Test
    void findSongs_ByCreatorId(){
        List<ReadSongDto> expected = List.of(
                ReadSongDto.builder().id(1L).build(),
                ReadSongDto.builder().id(2L).build(),
                ReadSongDto.builder().id(3L).build(),
                ReadSongDto.builder().id(4L).build(),
                ReadSongDto.builder().id(14L).build()
        );
        List<ReadSongDto> actual = songService.findSongsByCreatorId(CREATOR_ID);
        assertThat(actual).isEqualTo(expected);
    }
    @Order(3)
    @Test
    void song_saving(){
        var expected = songService.save(CreateSongDto.builder()
                .title("test")
                .description("testsong")
                .build(), null);
        var actual = songService.findSongById(16L);
        assertThat(actual).isEqualTo(expected);
    }
}
