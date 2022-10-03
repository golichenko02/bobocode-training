create table shortened_urls
(
    id           text primary key unique,
    original_url text not null unique,
    title        text,
    created_at   timestamp default now()
)