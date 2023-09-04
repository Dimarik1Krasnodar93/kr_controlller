CREATE TABLE IF NOT EXISTS
    my_users (
    id SERIAL PRIMARY KEY,
    username varchar(15),
    password varchar(100),
    enabled boolean
);