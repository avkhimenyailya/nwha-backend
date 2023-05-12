create table random_things_of_day
(
    id         serial primary key,
    thing_id      bigint not null,

    created_at timestamp not null,

    foreign key (thing_id) references things (id),
);