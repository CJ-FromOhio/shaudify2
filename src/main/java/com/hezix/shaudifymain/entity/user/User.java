package com.hezix.shaudifymain.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.song.Song;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@EqualsAndHashCode(exclude = {"createdSongs","likedSongs"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"createdSongs","likedSongs","albums"})
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String username;
    @Column()
    private String password;
    @Column()
    private String email;
    @Column()
    private String firstName;
    @Column
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "creator",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Song> createdSongs = new ArrayList<>();

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "liked_songs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private Set<Song> likedSongs = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "author",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE})
    private List<Album> albums = new ArrayList<>();
    @Column()
    private String image;

    @Column()
    private Instant createdAt;

}
