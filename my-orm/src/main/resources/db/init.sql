CREATE TABLE persons
(
    id         BIGINT PRIMARY KEY,
    first_name varchar(64),
    last_name  varchar(64),
    age        integer,
    created_at timestamp default now()
);

INSERT INTO persons(id, first_name, last_name, age) VALUES (1, 'Martin', 'Fowler', 58),
                                                           (2, 'Joshua', 'Bloch', 61),
                                                           (3, 'Herbert', 'Schildt', 71),
                                                           (4, 'Vlad', 'Mihalcea', 45),
                                                           (5, 'Robert', 'Martin', 70);