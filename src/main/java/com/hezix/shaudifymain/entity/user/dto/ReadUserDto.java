package com.hezix.shaudifymain.entity.user.dto;

import com.hezix.shaudifymain.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserDto {
    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private Role role;

//    private List<Song> createdSong = new ArrayList<>();

//    private List<LikedSong> likedSong = new ArrayList<>();

    private Instant createdAt;
}
