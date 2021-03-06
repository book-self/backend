-- Table for password reset tokens
create table password_reset_tokens (
    id int generated by default as identity,
    user_id int not null references users(id) on delete cascade,
    token varchar(255) unique not null,
    created date default CURRENT_DATE not null,
    expiration timestamp without time zone default (now() at time zone 'utc') not null
);