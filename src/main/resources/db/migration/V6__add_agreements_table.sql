create table agreements
(
    id                   serial primary key,
    profile_id           bigint    not null unique,

    created_at           timestamp not null,
    updated_at           timestamp,

    accepted_photo_usage boolean default false,

    foreign key (profile_id) references profiles (id)
);