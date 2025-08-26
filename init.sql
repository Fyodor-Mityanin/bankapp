create database accounts;

\connect accounts;

create table accounts (
    id          serial primary key,
    login       varchar(100) unique not null,
    password    varchar(255) not null
);

create table account_balance (
    id bigserial primary key,
    currency varchar(3) not null,
    amount numeric(15,2) not null default 0,
    account_id bigint not null,
    constraint fk_account foreign key (account_id) references accounts(id) on delete cascade
);

create index idx_account_balance_account_id on account_balance(account_id);

insert into accounts (login, password) values
-- пароль "password"
('user', '{bcrypt}$2a$10$ayC7NXp7qEIbJJb21ZZIe.a3f11IC/yEuOwWhrUuwK1TIP0ptITDq'),
-- пароль "password1"
('user1', '{bcrypt}$2a$10$EAyb8FfYBSQ74VBRgicIRePvd8szh9HN6ccyeKT4bUjEBJGyVjSu.'),
-- пароль "password2"
('user2', '{bcrypt}$2a$10$Aa/f6s83IxwRtjpsPyIl.uSr8UvAbuOhDkqTO2.lMdv5Ii2B8UUe6'),
-- пароль "password3"
('user3', '{bcrypt}$2a$10$wQAB87dwsz5nnXDcHJhSgOvIL6BtwD6j0yiarlPmLj.TYSZDRlgO2'),
-- пароль "password4"
('user4', '{bcrypt}$2a$10$6BvjbbgWwTy6/nRs/DN1dOnKFYKkvRNReuvKZ77YWbn6HEbb5Qxpy'),
-- пароль "password5"
('user5', '{bcrypt}$2a$10$qpq1te9H6GAfnpX4dKAFGuJt0VR5BosNqUXNuMFhXWNUOo5mJqUaG'),
-- пароль "admin123"
('admin', '{bcrypt}$2a$10$Pk.yhEYQ6XHEdvWz7aULXO1NuOBUxykZasRWzAyvTN9mfEuBvTgz2');
