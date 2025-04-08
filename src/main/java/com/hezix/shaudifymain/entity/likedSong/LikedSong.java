package com.hezix.shaudifymain.entity.likedSong;

import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
