package com.hezix.shaudifymain.integration.service;

import com.hezix.shaudifymain.BaseIntegrationTest;
import com.hezix.shaudifymain.annotations.Integration;
import com.hezix.shaudifymain.entity.song.Genre;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.Role;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.repository.SongRepository;
import com.hezix.shaudifymain.repository.UserRepository;
import com.hezix.shaudifymain.service.song.SongService;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.hezix.shaudifymain.util.filter.SongFilter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Integration
@Testcontainers
public class SongServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private SongService songService;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private UserRepository userRepository;

    private Long savedCreatorId;


    @BeforeEach
    void setUp(){
        songRepository.deleteAll();
        userRepository.deleteAll();

        String shortId = UUID.randomUUID().toString().substring(0, 8);
        var user = userRepository.save(User.builder()
                .username("username_" + shortId)
                .email("test_email" + UUID.randomUUID() + "@gmail.com")
                .role(Role.AUTHOR)
                .createdAt(Instant.now())
                .build());
        this.savedCreatorId = user.getId();
    }
    @Test
    void findById_throwIfNotExists(){
        assertThrows(EntityNotFoundException.class,
                () -> songService.findSongById(-1L));
    }
    @Test
    void findById(){
        var song = songRepository.save(Song.builder()
                        .title("Song1")
                        .creator(userRepository.getReferenceById(savedCreatorId))
                        .createdAt(Instant.now())
                .build());
        var actual = songService.findSongById(song.getId());

        assertThat(actual.getId()).isEqualTo(song.getId());
        assertThat(actual.getTitle()).isEqualTo("Song1");
    }
    @Test
    void findAll(){
        var author = userRepository.getReferenceById(savedCreatorId);
        songRepository.save(Song.builder().title("Song1").creator(author).build());
        songRepository.save(Song.builder().title("Song2").creator(author).build());
        var actual = songService.findAllSongs();
        assertThat(actual).hasSizeGreaterThanOrEqualTo(2);
    }
    @Test
    void findAll_byFilter(){
        String shortId = UUID.randomUUID().toString().substring(0, 8);
        String uniqueTitle = "FilterMe_" + shortId;
        songRepository.save(Song.builder().title(uniqueTitle).build());

        Pageable pageable = PageRequest.of(0, 10);
        var actual = songService.findAllSongsByFilter(new SongFilter(uniqueTitle), pageable);

        assertThat(actual).hasSize(1);
        assertThat(actual.getContent().get(0).getTitle()).isEqualTo(uniqueTitle);
    }
    @Test
    void findSongs_ByCreatorId(){
        var author = userRepository.getReferenceById(savedCreatorId);
        songRepository.save(Song.builder().title("AuthorSong1").creator(author).build());
        songRepository.save(Song.builder().title("AuthorSong2").creator(author).build());

        List<ReadSongDto> actual = songService.findSongsByCreatorId(author.getId());

        assertThat(actual).hasSize(2);
        assertThat(actual).extracting(ReadSongDto::getTitle)
                .containsExactlyInAnyOrder("AuthorSong1", "AuthorSong2");
    }
    @Test
    void song_saving(){
        var dto = CreateSongDto.builder()
                .title("SaveTestSong")
                .description("Description Song 1")
                .genre(Genre.POP)
                .build();
        var saved = songService.save(dto, null);
        var actual = songService.findSongById(saved.getId());
        assertThat(actual.getTitle()).isEqualTo("SaveTestSong");
        assertThat(actual.getId()).isNotNull();
    }
}
