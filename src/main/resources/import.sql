INSERT INTO permission (description, action) values ('dash', 0);
INSERT INTO permission (description, action) values ('dash', 1);
INSERT INTO permission (description, action) values ('dash', 2);
INSERT INTO permission (description, action) values ('dash', 3);
INSERT INTO permission (description, action) values ('form', 0);
INSERT INTO permission (description, action) values ('form', 1);
INSERT INTO permission (description, action) values ('form', 2);
INSERT INTO permission (description, action) values ('form', 3);

INSERT INTO USERS (email, name, password, role, status) values ('marcelonavarro11md@gmail.com', 'Marcelo Falchi', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
INSERT INTO USERS (email, name, password, role, status) values ('erickborges100@gmail.com', 'Erick Borges', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
INSERT INTO USERS (email, name, password, role, status) values ('pegoras@gmail.com', 'Marco Pegoraro', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
INSERT INTO USERS (email, name, password, role, status) values ('vini@gmail.com', 'Vini Braun', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
INSERT INTO USERS (email, name, password, role, status) values ('menozzo@gmail.com', 'Guilherme Minozzi', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
INSERT INTO USERS (email, name, password, role, status) values ('leomoreno@gmail.com', 'Leonardo Moreno', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
INSERT INTO USERS (email, name, password, role, status) values ('pegorini@gmail.com', 'Vini Pegorini', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 0, 1);
INSERT INTO USERS (email, name, password, role, status) values ('marcotoninho@gmail.com', 'Marco Antonio', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
INSERT INTO USERS (email, name, password, role, status) values ('fernanda@gmail.com', 'Fernanda', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
INSERT INTO USERS (email, name, password, role, status) values ('cathula@gmail.com', 'Cathula', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
INSERT INTO USERS (email, name, password, role, status) values ('external@gmail.com', 'External', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 2, 1);
INSERT INTO USERS (email, name, password, role, status) values ('teacher1@gmail.com', 'Teacher 1', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 0, 1);
INSERT INTO USERS (email, name, password, role, status) values ('partner@partner.com', 'Partner', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 4, 1);

-- Insere domínio de teste para vincular o type PARTNER quando criar um usuário com esse domínio no e-mail
INSERT INTO domain_role (domain, role) VALUES('partner.com', 4);

INSERT INTO project (description, subject) VALUES ('PROJECT DESCRIPTION', 'PROJECT SUBJECT');

INSERT INTO partner (name) VALUES ('FADEP');
INSERT INTO partner (name) VALUES ('UNIMATER');

-- Insere configuração de email para testes
INSERT INTO configemail(email_from, password_email_from, send_host, send_port) VALUES ('utfproficinareturn@gmail.com', 'gihwxsvqzzrwwdtv', 'smtp.gmail.com', 587);

