INSERT INTO ROLES (name, description, creation_date)
values ('ADMIN', 'Administrator Role', NOW()),
       ('USER', 'User Role', NOW());

INSERT INTO USERS (first_name, last_name, email, password, photo, role_id, deleted, creation_date)
values ('User1', 'User1', 'user1@gmail.com', '$2a$10$McgCwPpuDJ/8yE20V5GaeOGC45FVk.e70G2fmwr/KAZafw.gxhBYC',
        'user1.jpg', 2, false, NOW()),
        ('Admin1', 'Admin1', 'admin1@gmail.com', '$2a$10$YL1/O8eLEYrQy.5H1Rc2dO7umIhK3wKPdQoZWembDWFdgDOmcSZB6',
         'admin1.jpg',1,false, NOW());