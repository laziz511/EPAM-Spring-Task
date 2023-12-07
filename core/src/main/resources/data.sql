INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('Certificate 1', 'Description 1', 29.99, 7, '2023-01-01 10:00:00', '2023-01-05 15:30:00'),
       ('Certificate 2', 'Description 2', 49.99, 14, '2023-02-10 12:30:00', '2023-02-15 18:45:00'),
       ('Certificate 3', 'Description 3', 19.99, 5, '2023-03-20 08:45:00', '2023-03-25 14:15:00');


INSERT INTO tags (name)
VALUES ('Tag 1'),
       ('Tag 2'),
       ('Tag 3');


INSERT INTO gift_certificate_tags (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 3);
