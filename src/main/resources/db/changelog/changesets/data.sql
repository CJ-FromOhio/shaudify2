TRUNCATE TABLE users RESTART IDENTITY CASCADE;
TRUNCATE TABLE albums RESTART IDENTITY CASCADE;
TRUNCATE TABLE songs RESTART IDENTITY CASCADE;
TRUNCATE TABLE playlists RESTART IDENTITY CASCADE;

-- Тестовые пользователи (4 artists, 2 regular users, 1 admin)
INSERT INTO users (id, created_at, email, first_name, last_name, password, role, username)
VALUES
-- Артисты
(1, '2025-01-01 00:00:00+00', 'artist1@test.com', 'Taylor', 'Swift', '$2a$10$fakehash', 'AUTHOR', 'taylor'),
(2, '2025-01-02 00:00:00+00', 'artist2@test.com', 'The', 'Weeknd', '$2a$10$fakehash', 'AUTHOR', 'weeknd'),
(3, '2025-01-03 00:00:00+00', 'artist3@test.com', 'Billie', 'Eilish', '$2a$10$fakehash', 'AUTHOR', 'billie'),
(4, '2025-01-04 00:00:00+00', 'artist4@test.com', 'Kendrick', 'Lamar', '$2a$10$fakehash', 'AUTHOR', 'kendrick'),
-- Обычные пользователи
(5, '2025-01-05 00:00:00+00', 'user1@test.com', 'Alex', 'Listener', '$2a$10$fakehash', 'USER', 'alex'),
(6, '2025-01-06 00:00:00+00', 'user2@test.com', 'Sam', 'MusicFan', '$2a$10$fakehash', 'USER', 'sam'),
-- Админ
(7, '2025-01-07 00:00:00+00', 'admin@test.com', 'Admin', 'Super', '$2a$10$fakehash', 'ADMIN', 'admin');

-- Тестовые альбомы (по 2 на артиста)
INSERT INTO albums (id, description, title, created_at, author_id)
VALUES
-- Альбомы Taylor Swift
(1, 'Debut album', 'Taylor Swift', '2025-02-01 00:00:00+00', 1),
(2, 'Pop masterpiece', '1989', '2025-02-02 00:00:00+00', 1),
-- Альбомы The Weeknd
(3, 'Breakthrough mixtape', 'House of Balloons', '2025-02-03 00:00:00+00', 2),
(4, 'Commercial success', 'After Hours', '2025-02-04 00:00:00+00', 2),
-- Альбом Billie Eilish
(5, 'Debut EP', 'Don''t Smile at Me', '2025-02-05 00:00:00+00', 3),
(6, 'Grammy winner', 'When We All Fall Asleep', '2025-02-06 00:00:00+00', 3),
-- Альбом Kendrick Lamar
(7, 'Critically acclaimed', 'good kid, m.A.A.d city', '2025-02-07 00:00:00+00', 4),
(8, 'Pulitzer winner', 'DAMN.', '2025-02-08 00:00:00+00', 4);

INSERT INTO songs (id, created_at, description, title, genre, author_id, album_id)
VALUES (1, '2025-03-01 00:00:00+00', 'taylor', 'Tim McGraw', 'POP', 1, 1),
       (2, '2025-03-02 00:00:00+00', 'taylor', 'Teardrops on My Guitar', 'POP', 1, 1),
       (3, '2025-03-03 00:00:00+00', 'taylor', 'Shake It Off', 'POP', 1, 2),
       (4, '2025-03-04 00:00:00+00', 'taylor', 'Blank Space', 'POP', 1, 2),
       (5, '2025-03-05 00:00:00+00', 'weeknd', 'Wicked Games', 'POP', 2, 3),
       (6, '2025-03-06 00:00:00+00', 'weeknd', 'The Hills', 'POP', 2, 4),
       (7, '2025-03-07 00:00:00+00', 'weeknd', 'Blinding Lights', 'POP', 2, 4),
       (8, '2025-03-08 00:00:00+00', 'billie', 'Ocean Eyes', 'POP', 3, 5),
       (9, '2025-03-09 00:00:00+00', 'billie', 'Bury a Friend', 'POP', 3, 6),
       (10, '2025-03-10 00:00:00+00', 'billie', 'Bad Guy', 'POP', 3, 6),
       (11, '2025-03-11 00:00:00+00', 'kendrick', 'Sing About Me', 'POP', 4, 7),
       (12, '2025-03-12 00:00:00+00', 'kendrick', 'HUMBLE.', 'POP', 4, 8),
       (13, '2025-03-13 00:00:00+00', 'kendrick', 'DNA.', 'POP', 4, 8),
       (14, '2025-03-14 00:00:00+00', 'taylor', 'All Too Well (10 Minute)', 'POP', 1, NULL),
       (15, '2025-03-15 00:00:00+00', 'weeknd', 'Moth to a Flame', 'POP', 2, NULL);

-- Тестовые плейлисты (публичные и приватные)
INSERT INTO playlists (id, created_at, image, title, type, author)
VALUES
-- Публичные плейлисты
(1, '2025-04-01 00:00:00+00', 'pop_hits.jpg', 'Top Pop Hits 2025', 'PUBLIC', 5),
(2, '2025-04-02 00:00:00+00', 'r_b_jams.jpg', 'R&B Essentials', 'PUBLIC', 6),
-- Приватные плейлисты
(3, '2025-04-03 00:00:00+00', 'workout.jpg', 'My Workout Mix', 'PRIVATE', 5),
(4, '2025-04-04 00:00:00+00', 'chill.jpg', 'Chill Vibes', 'PRIVATE', 6);

-- Связи песен с плейлистами
INSERT INTO playlist_songs (playlist_id, song_id)
VALUES
-- Top Pop Hits
(1, 3), -- Shake It Off
(1, 4), -- Blank Space
(1, 7), -- Blinding Lights
(1, 10), -- Bad Guy
-- R&B Essentials
(2, 5), -- Wicked Games
(2, 6), -- The Hills
-- Workout Mix
(3, 7), -- Blinding Lights
(3, 12), -- HUMBLE.
(3, 13), -- DNA.
-- Chill Vibes
(4, 8), -- Ocean Eyes
(4, 14);
-- All Too Well

-- Лайки песен (перекрестные лайки между пользователями)
INSERT INTO liked_songs (song_id, user_id)
VALUES (3, 5),
       (7, 5),
       (10, 5),
       (12, 6),
       (4, 6),
       (14, 6);
-- Sam likes All Too Well

-- Лайки плейлистов
INSERT INTO liked_playlists (user_id, playlist_id)
VALUES (5, 2), -- Alex likes R&B Essentials
       (6, 1); -- Sam likes Top Pop Hits