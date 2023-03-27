create table refresh_tokens
(
    id         serial primary key,

    user_id    bigint    not null,
    token      text      not null unique,

    created_at timestamp not null,
    updated_at timestamp,

    foreign key (user_id) references users (id)
);