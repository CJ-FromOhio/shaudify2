--liquibase formatted sql

ALTER SEQUENCE users_id_seq RESTART WITH 8;
ALTER SEQUENCE songs_id_seq RESTART WITH 16;
ALTER SEQUENCE albums_id_seq RESTART WITH 9;
ALTER SEQUENCE playlists_id_seq RESTART WITH 5;