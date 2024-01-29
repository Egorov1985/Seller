insert into buysell.user (id, email, name, password, phone_number, active, date_of_created)
VALUES (1,
        'admin@mail.com',
        'admin',
        '$2a$08$YzWKyBDFRpaW9Pu2hcme8eTzv7gk/Btr17AEJYVoU5ygJJLAQO/9e',
        '+79999999999',
        1,
        current_timestamp),
       (2,
        'user1@mail.com',
        'user1',
        '$2a$08$YNtsrOmXCmef6AKJBjQuIu.2G4pjY7N.zS93kAlmIlOqb/MqGJgZm',
        '+79999999996',
        1,
        current_timestamp),
       (3,
        'user2@mail.com',
        'user2',
        '$2a$08$kyAsJoT2VIB9NWdZ1cq4KO6BalMzS5kRL.XN9hStZdbysJ1sD.ZN2',
        '+79999999997',
        1,
        current_timestamp);

insert into buysell.user_role(user_id, roles)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_USER');



