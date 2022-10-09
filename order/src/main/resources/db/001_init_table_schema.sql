create table orders(
    id          serial primary key,
    created     timestamp,
    userEmail   text,
    status      text
);
