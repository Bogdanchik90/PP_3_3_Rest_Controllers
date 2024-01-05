CREATE TABLE IF NOT EXISTS person
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    user_name     VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255)  NOT NULL,
    age      INT      NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
    );
INSERT INTO person(user_name, last_name, age, email, password)
VALUES ('user', 'user', 30, 'user@mail.ru', 'user');
INSERT INTO person(user_name, last_name, age, email, password)
VALUES ('admin', 'admin', 30, 'admin@mail.ru', 'admin');


CREATE TABLE IF NOT EXISTS roles
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL
    );
INSERT INTO roles(name)
    VALUE ('ROLE_USER');
INSERT INTO roles(name)
    VALUE ('ROLE_ADMIN');


CREATE TABLE person_roles
(
    person_id INT,
    role_id INT,
    PRIMARY KEY (person_id, role_id),
    FOREIGN KEY (person_id) REFERENCES person (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);
INSERT INTO person_roles(person_id, role_id)
    VALUES (1,1);
INSERT INTO person_roles(person_id, role_id)
    VALUES (2,2);


DROP TABLE IF EXISTS person_roles;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS roles;