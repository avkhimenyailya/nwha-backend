create table tg_bot_users
(
    id         serial primary key,

    username   varchar(255) not null unique,
    chat_id    bigint       not null,

    created_at timestamp    not null
);