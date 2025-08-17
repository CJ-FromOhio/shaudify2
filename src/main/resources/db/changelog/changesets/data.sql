-- Тестовые пользователи (4 artists, 2 regular users, 1 admin)
INSERT INTO users (id, created_at, email, first_name, last_name, password, role, username) VALUES
-- Артисты
(1, '2025-01-01 00:00:00+00', 'artist1@test.com', 'Taylor', 'Swift', '$2a$10$fakehash', 'ARTIST', 'taylor'),
(2, '2025-01-02 00:00:00+00', 'artist2@test.com', 'The', 'Weeknd', '$2a$10$fakehash', 'ARTIST', 'weeknd'),
(3, '2025-01-03 00:00:00+00', 'artist3@test.com', 'Billie', 'Eilish', '$2a$10$fakehash', 'ARTIST', 'billie'),
(4, '2025-01-04 00:00:00+00', 'artist4@test.com', 'Kendrick', 'Lamar', '$2a$10$fakehash', 'ARTIST', 'kendrick'),
-- Обычные пользователи
(5, '2025-01-05 00:00:00+00', 'user1@test.com', 'Alex', 'Listener', '$2a$10$fakehash', 'USER', 'alex'),
(6, '2025-01-06 00:00:00+00', 'user2@test.com', 'Sam', 'MusicFan', '$2a$10$fakehash', 'USER', 'sam'),
-- Админ
(7, '2025-01-07 00:00:00+00', 'admin@test.com', 'Admin', 'Super', '$2a$10$fakehash', 'ADMIN', 'admin');

-- Тестовые альбомы (по 2 на артиста)
INSERT INTO albums (id, description, title, created_at, author_id) VALUES
-- Альбомы Taylor Swift
(101, 'Debut album', 'Taylor Swift', '2025-02-01 00:00:00+00', 1),
(102, 'Pop masterpiece', '1989', '2025-02-02 00:00:00+00', 1),
-- Альбомы The Weeknd
(201, 'Breakthrough mixtape', 'House of Balloons', '2025-02-03 00:00:00+00', 2),
(202, 'Commercial success', 'After Hours', '2025-02-04 00:00:00+00', 2),
-- Альбом Billie Eilish
(301, 'Debut EP', 'Don''t Smile at Me', '2025-02-05 00:00:00+00', 3),
(302, 'Grammy winner', 'When We All Fall Asleep', '2025-02-06 00:00:00+00', 3),
-- Альбом Kendrick Lamar
(401, 'Critically acclaimed', 'good kid, m.A.A.d city', '2025-02-07 00:00:00+00', 4),
(402, 'Pulitzer winner', 'DAMN.', '2025-02-08 00:00:00+00', 4);

-- Тестовые песни (по 3 на альбом + 2 сингла без альбома)
INSERT INTO songs (id, created_at, description, title, genre, author_id, album_id) VALUES
-- Песни Taylor Swift
(1001, '2025-03-01 00:00:00+00', 'taylor', 'Debut single', 'Tim McGraw', 'COUNTRY', 1, 101),
(1002, '2025-03-02 00:00:00+00', 'taylor', 'Fan favorite', 'Teardrops on My Guitar', 'COUNTRY', 1, 101),
(1003, '2025-03-03 00:00:00+00', 'taylor', 'Hit single', 'Shake It Off', 'POP', 1, 102),
(1004, '2025-03-04 00:00:00+00', 'taylor', 'Chart topper', 'Blank Space', 'POP', 1, 102),
-- Песни The Weeknd
(2001, '2025-03-05 00:00:00+00', 'weeknd', 'Signature song', 'Wicked Games', 'R&B', 2, 201),
(2002, '2025-03-06 00:00:00+00', 'weeknd', 'Fan favorite', 'The Hills', 'R&B', 2, 202),
(2003, '2025-03-07 00:00:00+00', 'weeknd', 'Hit single', 'Blinding Lights', 'POP', 2, 202),
-- Песни Billie Eilish
(3001, '2025-03-08 00:00:00+00', 'billie', 'Breakthrough hit', 'Ocean Eyes', 'ELECTRO', 3, 301),
(3002, '2025-03-09 00:00:00+00', 'billie', 'Dark pop', 'Bury a Friend', 'ELECTRO', 3, 302),
(3003, '2025-03-10 00:00:00+00', 'billie', 'Grammy winner', 'Bad Guy', 'ELECTRO', 3, 302),
-- Песни Kendrick Lamar
(4001, '2025-03-11 00:00:00+00', 'kendrick', 'Storytelling', 'Sing About Me', 'HIP_HOP', 4, 401),
(4002, '2025-03-12 00:00:00+00', 'kendrick', 'Pulitzer winner', 'HUMBLE.', 'HIP_HOP', 4, 402),
(4003, '2025-03-13 00:00:00+00', 'kendrick', 'Fan favorite', 'DNA.', 'HIP_HOP', 4, 402),
-- Синглы без альбомов
(5001, '2025-03-14 00:00:00+00', 'taylor', 'Surprise release', 'All Too Well (10 Minute)', 'POP', 1, NULL),
(5002, '2025-03-15 00:00:00+00', 'weeknd', 'Collab single', 'Moth to a Flame', 'POP', 2, NULL);

-- Тестовые плейлисты (публичные и приватные)
INSERT INTO playlists (id, created_at, image, title, type, author) VALUES
-- Публичные плейлисты
(10001, '2025-04-01 00:00:00+00', 'pop_hits.jpg', 'Top Pop Hits 2025', 'PUBLIC', 5),
(10002, '2025-04-02 00:00:00+00', 'r_b_jams.jpg', 'R&B Essentials', 'PUBLIC', 6),
-- Приватные плейлисты
(20001, '2025-04-03 00:00:00+00', 'workout.jpg', 'My Workout Mix', 'PRIVATE', 5),
(20002, '2025-04-04 00:00:00+00', 'chill.jpg', 'Chill Vibes', 'PRIVATE', 6);

-- Связи песен с плейлистами
INSERT INTO playlist_songs (playlist_id, song_id) VALUES
-- Top Pop Hits
(10001, 1003), -- Shake It Off
(10001, 1004), -- Blank Space
(10001, 2003), -- Blinding Lights
(10001, 3003), -- Bad Guy
-- R&B Essentials
(10002, 2001), -- Wicked Games
(10002, 2002), -- The Hills
-- Workout Mix
(20001, 2003), -- Blinding Lights
(20001, 4002), -- HUMBLE.
(20001, 4003), -- DNA.
-- Chill Vibes
(20002, 3001), -- Ocean Eyes
(20002, 5001); -- All Too Well

-- Лайки песен (перекрестные лайки между пользователями)
INSERT INTO liked_songs (song_id, user_id) VALUES
                                                   (1, 1003, 5), -- Alex likes Shake It Off
                                                   (2, 2003, 5), -- Alex likes Blinding Lights
                                                   (3, 3003, 5), -- Alex likes Bad Guy
                                                   (4, 4002, 6), -- Sam likes HUMBLE.
                                                   (5, 1004, 6), -- Sam likes Blank Space
                                                   (6, 5001, 6); -- Sam likes All Too Well

-- Лайки плейлистов
INSERT INTO liked_playlists (user_id, playlist_id) VALUES
                                                       (5, 10002), -- Alex likes R&B Essentials
                                                       (6, 10001); -- Sam likes Top Pop Hits