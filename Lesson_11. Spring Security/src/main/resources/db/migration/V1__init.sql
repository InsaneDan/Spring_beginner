create table users (
  id                    bigserial,
  username              varchar(30) not null unique,
  password              varchar(80) not null,
  email                 varchar(50) unique,
  primary key (id)
);

create table roles (
  id                    serial,
  name                  varchar(50) not null,
  primary key (id)
);

create table users_roles (
  user_id               bigint not null,
  role_id               int not null,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);

insert into roles (name)
values
('ROLE_USER'), ('ROLE_MANAGER'), ('ROLE_ADMIN'), ('ROLE_SUPERADMIN');

insert into users (username, password, email)
values
('user', '$2y$12$3jD2vqhTkyn3nYy4XmMUXO9V8LaW1gQPsiC8vqaanV7Q05dydOY9C', 'user@gmail.com'),
('manager', '$2y$12$3jD2vqhTkyn3nYy4XmMUXO9V8LaW1gQPsiC8vqaanV7Q05dydOY9C', 'manager@gmail.com'),
('admin', '$2y$12$3jD2vqhTkyn3nYy4XmMUXO9V8LaW1gQPsiC8vqaanV7Q05dydOY9C', 'admin@gmail.com'),
('superadmin', '$2y$12$3jD2vqhTkyn3nYy4XmMUXO9V8LaW1gQPsiC8vqaanV7Q05dydOY9C', 'superadmin@gmail.com');
-- 123
insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2),
(3, 3),
(4, 4);

create table products (
    id                    bigserial,
    title                 varchar(255) not null,
    price                 decimal(6,2) not null,
    primary key (id));

insert into products (title, price)
values
('Product1', 10),
('Product2', 20),
('Product3', 30),
('Product4', 40);