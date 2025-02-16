-- liquibase formatted sql
-- changeset eve:1.1

insert into users (name, email, password, role, phone_number)
values ('Specialist1', 'spec@mail.ru', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W', 'SPECIALIST', '88005553535'),
       ('Specialist2', 'spec.second@mail.ru', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W', 'SPECIALIST', '88005553536'),
       ('User1', 'user@mail.ru', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W', 'USER', '88005553537'),
       ('User2', 'user.second@mail.ru', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W', 'USER', '88005553538');