INSERT INTO roles(name)
VALUES ("ROLE_ADMIN"),
       ("ROLE_SUPER_ADMIN"),
       ("ROLE_MANAGER");

INSERT INTO users(login, password) VALUES
("admin", "$2y$12$eLi26HT/0T.vhBPQ5CX9meQByD/rfg7..psmUeemuFJszqob95Kme"),
("superadmin", "$2y$12$RwohbWxXcY22nLIYSHw5vOVxJ1RAHLOWAdIntQt.uz/hVjV3Kp.Ba"),
("manager", "$2y$12$QLQFgomdxV6VAc8/8iqf6e38FuO6TUUsk/F7LTOySmGKI7d9gLV9O");

INSERT INTO users_roles(user_id, role_id)
VALUES (1, 1), (2, 2), (3, 3);