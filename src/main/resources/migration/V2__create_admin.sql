INSERT INTO USER
    (email, phone_number, name, active, password, date_Of_Created)
VALUES ('admin@admin.com', '+79999999999', 'admin', 1, AES_ENCRYPT ('1'), current_timestamp());

INSERT INTO USER_ROLE
    (user_id, roles)
VALUES ((select id from user where name = 'admin'), 'ROLE_ADMIN');