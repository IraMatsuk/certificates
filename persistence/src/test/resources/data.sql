INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('certificateOne', 'descriptionOne', 2100.50, 21, '2022-11-22 12:06:45', '2022-11-22 12:06:45'),
       ('certificateTwo', 'descriptionTwo', 4100.90, 10, '2022-10-23 09:16:05', '2022-10-23 09:16:05');

INSERT INTO tag(name) VALUES ('oneTag');
INSERT INTO tag(name) VALUES ('twoTag');
INSERT INTO tag(name) VALUES ('treeTag');
INSERT INTO tag(name) VALUES ('fourTag');
INSERT INTO tag(name) VALUES ('fiveTag');

INSERT INTO tag_gift_certificate(gift_certificate_id, tag_id)
VALUES (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (1, 4),
       (1, 5);

