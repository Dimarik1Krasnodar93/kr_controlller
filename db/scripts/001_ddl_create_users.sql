CREATE TABLE
    users (
    id SERIAL PRIMARY KEY,
    username varchar(15),
    password varchar(100),
    enabled boolean
);