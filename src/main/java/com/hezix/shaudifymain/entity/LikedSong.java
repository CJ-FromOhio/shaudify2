package com.hezix.shaudifymain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "likedSongs")
public class LikedSong {
    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;
}
