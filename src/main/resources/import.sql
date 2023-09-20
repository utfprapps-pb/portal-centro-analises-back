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
INSERT INTO USERS (email, name, password, role, status) values ('lab@gmail.com', 'Lab', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 3, 1);
INSERT INTO USERS (email, name, password, role, status) values ('teacher1@gmail.com', 'Teacher 1', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 0, 1);

-- Carga inicial equipamentos
INSERT INTO equipment (name, value_hour_utfpr, value_hour_partner, value_sample_pf_pj, status) VALUES ('Cromatógrafo líquido de alta eficiência - HPLC (modelo LC920, Varian)', 11.50, 21.00, 115.00, 1);
INSERT INTO equipment (name, value_hour_utfpr, value_hour_partner, value_sample_pf_pj, status) VALUES ('Cromatógrafo a gás acoplado com espectrômetro de massa (CG-EM) (modelo 431GC-210MS, Varian)', 15.00, 25.00, 150.00, 1);
INSERT INTO equipment (name, value_hour_utfpr, value_hour_partner, value_sample_pf_pj, status) VALUES ('Analisador termogravimétrico - TGA-DTA-DSC (modelo SDTQ600, TA Instruments)', 14.00, 22.00, 80.00, 1);
INSERT INTO equipment (name, value_sample_utfpr, value_sample_partner, value_sample_pf_pj, status) VALUES ('Calorímetro diferencial exploratório - DSC (modelo Q20, TA instruments)', 30.00, 45.00, 110.00, 1);
INSERT INTO equipment (name, value_hour_utfpr, value_hour_partner, value_sample_pf_pj, status) VALUES ('Microscópio Eletrônico de Varredura – MEV (modelo TM3000, Hitachi)', 17.00, 25.00, 80.00, 1);
INSERT INTO equipment (name, value_hour_utfpr, value_hour_partner, value_sample_pf_pj, status) VALUES ('Difratômetro de Raios-X - DRX (modelo Miniflex 600, Rigaku)', 25.00, 45.00, 105.00, 1);
INSERT INTO equipment (name, value_hour_utfpr, value_hour_partner, value_sample_pf_pj, status) VALUES ('Espectrômetro de Infravermelho na região do próximo com Transformada de Fourier - FT-NIR (modelo MPA, BRUKER)', 2.50, 5.00, 15.00, 1);
INSERT INTO equipment (name, value_hour_utfpr, value_hour_partner, value_sample_pf_pj, status) VALUES ('Espectrômetro de Infravermelho na região do Médio com Transformada de Fourier - FT-MIR (modelo Frontier, Perkin Elmer)', 5.00, 15.00, 130.00, 1);
INSERT INTO equipment (name, value_hour_utfpr, value_hour_partner, value_sample_pf_pj, status) VALUES ('Espectrofotômetro de Absorção Molecular - UV-Vis (modelo LAMBDA 45, Perkin Elmer)', 8.00, 15.00, 70.00, 1);
INSERT INTO equipment (name, value_hour_utfpr, value_hour_partner, value_sample_pf_pj, status) VALUES ('Espectrômetro de Absorção Atômica - A ATÔMICA (modelo PinAAcle 900T, Perkin Elmer)', 18.00, 30.00, 80.00, 1);
INSERT INTO equipment (name, value_sample_utfpr, value_sample_partner, value_sample_pf_pj, status) VALUES ('Analisador elementar de CHNS-O - A ELEMENTAR (modelo Euro EA, Euro Vector)', 40.00, 60.00, 140.00, 1);

INSERT INTO project (description, subject) VALUES ('PROJECT DESCRIPTION', 'PROJECT SUBJECT');

INSERT INTO partner (name) VALUES ('FADEP');
INSERT INTO partner (name) VALUES ('UNIMATER');