create table dish
(
    id   serial primary key,
    name text unique,
    ingredients text,
    price integer
);

