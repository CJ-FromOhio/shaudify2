package com.hezix.shaudifymain.entity.album;

public enum ALBUM_TYPE {
    SINGLE("single"),
    ALBUM("album"),
    MINI_ALBUM("mini-album");

    private final String displayName;

    ALBUM_TYPE(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
