INSERT INTO permission (description) values ('access_dash');
INSERT INTO permission (description) values ('access_form');
INSERT INTO permission (description) values ('read_dash');
INSERT INTO permission (description) values ('read_form');
INSERT INTO permission (description) values ('delete_dash');
INSERT INTO permission (description) values ('delete_form');
INSERT INTO permission (description) values ('update_dash');
INSERT INTO permission (description) values ('update_form');

INSERT INTO USERS (email, name, password, username, role) values ('marcelonavarro11md@gmail.com', 'Marcelo Falchi', '$2a$10$Iu9s/vE3UT4SMlwuLN1tyuxkmV.TqEGEC93Knrfcm5SXYThnDuU1i', 'falchi', 1);
INSERT INTO USERS (email, name, password, username, role) values ('erickborges@gmail.com', 'Erick Borges', '$2a$10$Iu9s/vE3UT4SMlwuLN1tyuxkmV.TqEGEC93Knrfcm5SXYThnDuU1i', 'erick', 1);
INSERT INTO USERS (email, name, password, username, role) values ('pegoras@gmail.com', 'Marco Pegoraro', '$2a$10$Iu9s/vE3UT4SMlwuLN1tyuxkmV.TqEGEC93Knrfcm5SXYThnDuU1i', 'pegoraro', 1);
INSERT INTO USERS (email, name, password, username, role) values ('vini@gmail.com', 'Vini Braun', '$2a$10$Iu9s/vE3UT4SMlwuLN1tyuxkmV.TqEGEC93Knrfcm5SXYThnDuU1i', 'vini_braun', 1);
INSERT INTO USERS (email, name, password, username, role) values ('menozzo@gmail.com', 'Guilherme Minozzi', '$2a$10$Iu9s/vE3UT4SMlwuLN1tyuxkmV.TqEGEC93Knrfcm5SXYThnDuU1i', 'menozzo', 1);
INSERT INTO USERS (email, name, password, username, role) values ('leomoreno@gmail.com', 'Leonardo Moreno', '$2a$10$Iu9s/vE3UT4SMlwuLN1tyuxkmV.TqEGEC93Knrfcm5SXYThnDuU1i', 'leo_moreno', 1);
INSERT INTO USERS (email, name, password, username, role) values ('pegorini@gmail.com', 'Vini Pegorini', '$2a$10$Iu9s/vE3UT4SMlwuLN1tyuxkmV.TqEGEC93Knrfcm5SXYThnDuU1i', 'pegorini', 0);
INSERT INTO USERS (email, name, password, username, role) values ('marcotoninho@gmail.com', 'Marco Antonio', '$2a$10$Iu9s/vE3UT4SMlwuLN1tyuxkmV.TqEGEC93Knrfcm5SXYThnDuU1i', 'toninho', 1);
INSERT INTO USERS (email, name, password, username, role) values ('fernanda@gmail.com', 'Fernanda', '$2a$10$Iu9s/vE3UT4SMlwuLN1tyuxkmV.TqEGEC93Knrfcm5SXYThnDuU1i', 'fernanda', 1);
INSERT INTO USERS  (email, name, password, username, role) values ('cathula@gmail.com', 'Cathula', '$2a$10$Iu9s/vE3UT4SMlwuLN1tyuxkmV.TqEGEC93Knrfcm5SXYThnDuU1i', 'cathula', 1);

insert into user_authorities (tb_user_id, authority_id) values (1,1);
insert into user_authorities (tb_user_id, authority_id) values (1,2);
insert into user_authorities (tb_user_id, authority_id) values (1,3);
insert into user_authorities (tb_user_id, authority_id) values (1,4);
insert into user_authorities (tb_user_id, authority_id) values (1,5);
insert into user_authorities (tb_user_id, authority_id) values (1,6);
insert into user_authorities (tb_user_id, authority_id) values (1,7);
insert into user_authorities (tb_user_id, authority_id) values (1,8);

insert into user_authorities (tb_user_id, authority_id) values (4,1);
insert into user_authorities (tb_user_id, authority_id) values (4,2);
insert into user_authorities (tb_user_id, authority_id) values (4,3);
insert into user_authorities (tb_user_id, authority_id) values (4,4);
insert into user_authorities (tb_user_id, authority_id) values (4,5);
insert into user_authorities (tb_user_id, authority_id) values (4,6);
insert into user_authorities (tb_user_id, authority_id) values (4,7);
insert into user_authorities (tb_user_id, authority_id) values (4,8);

insert into PARTNERS (name) values ( 'FADEP' );