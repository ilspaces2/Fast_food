create table notification(
    id          serial primary key,
    notification_type     text,
    item_id_from_service   int,
    status     text
);
