CREATE TABLE ROLES
(
    id            INTEGER      NOT NULL AUTO_INCREMENT,
    name          VARCHAR(128) NOT NULL,
    description   VARCHAR(128) NOT NULL,
    creation_date VARCHAR(128),
    PRIMARY KEY (id)
);

CREATE TABLE USERS
(
    id            INTEGER      NOT NULL AUTO_INCREMENT,
    first_name    VARCHAR(128) NOT NULL,
    creation_date DATE,
    deleted       BOOLEAN,
    photo         VARCHAR(128),
    last_name     VARCHAR(128) NOT NULL,
    email         VARCHAR(128) NOT NULL UNIQUE,
    password      VARCHAR(128) NOT NULL,
    role_id       BIGINT,
    PRIMARY KEY (id)
);
