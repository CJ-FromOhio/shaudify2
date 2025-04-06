package com.hezix.shaudifymain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User creator;

    @Column(nullable = false)
    private Instant createdAt;
    @Column()
    private String createdBy;
}
