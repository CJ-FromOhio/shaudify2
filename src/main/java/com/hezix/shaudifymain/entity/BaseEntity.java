package com.hezix.shaudifymain.entity;

import jakarta.persistence.Column;

import java.time.Instant;

public abstract class BaseEntity {
    @Column()
    private String createdBy;
    @Column()
    private String modifiedBy;
    @Column(nullable = false)
    private Instant createdAt;
    @Column()
    private Instant modifiedAt;
}
