package com.hezix.shaudifymain.entity.albumSong;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hezix.shaudifymain.entity.album.Album;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "albumSongs")
public class AlbumSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "song_id")
    @JsonManagedReference
    @ManyToOne
    private Song song;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
}
