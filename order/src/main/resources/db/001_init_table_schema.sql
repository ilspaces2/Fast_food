create table orders(
    id          serial primary key,
    created     timestamp,
    user_email   text,
    status      text
);
