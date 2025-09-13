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

create database exchange;

\connect exchange;

-- Справочник валют
CREATE TABLE currencies
(
    code VARCHAR(3) PRIMARY KEY, -- RUB, USD, CNY
    name TEXT NOT NULL           -- Рубль, Доллар США, Юань
);

-- История курсов валют
CREATE TABLE exchange_rates
(
    id            BIGSERIAL PRIMARY KEY,
    currency_from VARCHAR(3)     NOT NULL,
    currency_to   VARCHAR(3)     NOT NULL,
    rate          NUMERIC(18, 6) NOT NULL,
    created_at    TIMESTAMPTZ    NOT NULL DEFAULT now(),
    CONSTRAINT fk_currency_from FOREIGN KEY (currency_from) REFERENCES currencies (code),
    CONSTRAINT fk_currency_to FOREIGN KEY (currency_to) REFERENCES currencies (code),
    CONSTRAINT chk_currency CHECK (currency_from <> currency_to)
);

-- Индексы
CREATE INDEX idx_exchange_rates_from_to ON exchange_rates(currency_from, currency_to);
CREATE INDEX idx_exchange_rates_created_at ON exchange_rates(created_at);

-- Наполнение справочника валют
INSERT INTO currencies (code, name)
VALUES ('RUB', 'Рубль'),
       ('USD', 'Доллар США'),
       ('CNY', 'Китайский юань');

