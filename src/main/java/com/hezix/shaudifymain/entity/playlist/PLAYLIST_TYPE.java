package com.hezix.shaudifymain.entity.playlist;

public enum PLAYLIST_TYPE {
    PUBLIC("public"),
    PRIVATE("private");
    private final String displayName;
    PLAYLIST_TYPE(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
