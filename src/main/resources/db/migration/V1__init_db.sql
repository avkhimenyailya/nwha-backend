create table users
(
    id              serial primary key,

    username        varchar(32)  not null unique,
    password        text not null, -- bcrypt
    invitation_code varchar(128) not null unique,

    created_at      timestamp    not null,
    updated_at      timestamp
);

create table roles
(
    id   serial primary key,
    name varchar(32)
);

create table users_to_roles
(
    user_id bigint not null,
    role_id bigint not null,

    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

create table users_invitations
(
    id              serial primary key,
    inviter_user_id bigint    not null, -- 5 invitations max
    invited_user_id bigint    not null,

    created_at      timestamp not null,

    foreign key (inviter_user_id) references users (id),
    foreign key (invited_user_id) references users (id)
);

create table profiles
(
    id          serial primary key,
    user_id     bigint    not null unique,

    description text,

    removed     boolean default false,
    created_at  timestamp not null,
    updated_at  timestamp,

    foreign key (user_id) references users (id)
);

create table traits
(
    id   serial primary key,

    name varchar(32) -- such as introvert
);

create table profiles_traits
(
    id         serial primary key,
    trait_id   bigint    not null,
    profile_id bigint    not null,

    value      int       not null, -- percentage

    created_at timestamp not null,
    updated_at timestamp,

    foreign key (trait_id) references traits (id),
    foreign key (profile_id) references profiles (id)
);

create table tasks
(
    id             serial primary key,

    description    text      not null unique,
    ordinal_number int       not null unique,

    removed        boolean default false,
    created_at     timestamp not null,
    updated_at     timestamp
);

create table profiles_tasks
(
    id         serial primary key,
    task_id    bigint    not null,
    profile_id bigint    not null,

    removed    boolean default false,
    created_at timestamp not null,
    updated_at timestamp,

    foreign key (task_id) references tasks (id),
    foreign key (profile_id) references profiles (id)
);

create table questions
(
    id             serial primary key,
    task_id        bigint    not null,

    ordinal_number int       not null unique,

    removed        boolean default false,
    created_at     timestamp not null,
    updated_at     timestamp,

    foreign key (task_id) references tasks (id)
);

create table options
(
    id          serial primary key,
    question_id bigint not null,

    description text   not null unique,

    foreign key (question_id) references questions (id)
);

create table answers
(
    id              serial primary key,
    option_id       bigint    not null,
    profile_task_id bigint    not null,

    created_at      timestamp not null,

    foreign key (option_id) references options (id),
    foreign key (profile_task_id) references profiles_tasks (id)
);

-- i +1 +3 +2 / i = sum(value_1) / (sum(value_1) + sum(value_2)) = (+1 +3 +2) / ((+1 +3 +2) + (+1 +1 +1))
-- e +1 +1 +1 / e = sum(value_2) / (sum(value_1) + sum(value_2)) = (+1 +1 +1) / ((+1 +3 +2) + (+1 +1 +1))
create table answers_values
(
    id        serial primary key,
    trait_id  bigint not null,
    option_id bigint not null,

    value     int    not null default 1,

    foreign key (trait_id) references traits (id),
    foreign key (option_id) references options (id)
);

create table things
(
    id              serial primary key,
    profile_task_id bigint    not null,

    file_url        text,
    description     text,
    archived        boolean default false,

    removed         boolean default false,
    created_at      timestamp not null,
    updated_at      timestamp,

    foreign key (profile_task_id) references profiles_tasks (id)
);

create table collections
(
    id         serial primary key,
    profile_id bigint      not null,

    name       varchar(32) not null,

    removed    boolean default false,
    created_at timestamp   not null,
    updated_at timestamp,

    foreign key (profile_id) references profiles (id)
);

create table things_to_collections
(
    thing_id      bigint not null,
    collection_id bigint not null,

    foreign key (thing_id) references things (id),
    foreign key (collection_id) references collections (id)
);
