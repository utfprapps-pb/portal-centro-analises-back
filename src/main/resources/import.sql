-- INSERT INTO permission (description, action) values ('dash', 0);
-- INSERT INTO permission (description, action) values ('dash', 1);
-- INSERT INTO permission (description, action) values ('dash', 2);
-- INSERT INTO permission (description, action) values ('dash', 3);
-- INSERT INTO permission (description, action) values ('form', 0);
-- INSERT INTO permission (description, action) values ('form', 1);
-- INSERT INTO permission (description, action) values ('form', 2);
-- INSERT INTO permission (description, action) values ('form', 3);
--
-- INSERT INTO USERS (email, name, password, role, status) values ('marcelonavarro11md@gmail.com', 'Marcelo Falchi', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('erickborges100@gmail.com', 'Erick Borges', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('pegoras@gmail.com', 'Marco Pegoraro', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('vini@gmail.com', 'Vini Braun', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('menozzo@gmail.com', 'Guilherme Minozzi', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('leomoreno@gmail.com', 'Leonardo Moreno', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('pegorini@gmail.com', 'Vini Pegorini', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 0, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('marcotoninho@gmail.com', 'Marco Antonio', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('fernanda@gmail.com', 'Fernanda', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('cathula@gmail.com', 'Cathula', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 1, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('external@gmail.com', 'External', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 2, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('teacher1@gmail.com', 'Teacher 1', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 0, 1);
-- INSERT INTO USERS (email, name, password, role, status) values ('partner@partner.com', 'Partner', '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C', 4, 1);

-- Insere domínio de teste para vincular o type PARTNER quando criar um usuário com esse domínio no e-mail
-- INSERT INTO tb_domain_role (domain, role) VALUES('partner.com', 4);

-- INSERT INTO project (description, subject) VALUES ('PROJECT DESCRIPTION', 'PROJECT SUBJECT');
--
-- INSERT INTO partner (name) VALUES ('FADEP');
-- INSERT INTO partner (name) VALUES ('UNIMATER');
--
-- -- Insere configuração de email para testes
-- INSERT INTO configemail(email_from, password_email_from, send_host, send_port) VALUES ('utfproficinareturn@gmail.com', 'gihwxsvqzzrwwdtv', 'smtp.gmail.com', 587);
--

INSERT INTO public.tb_equipment (status,value_hour_partner,value_hour_utfpr,value_sample_external,id,model,"name",short_name) VALUES (1,21,11.5,115,1,'LC920, Varian','Cromatógrafo líquido de alta eficiência','HPLC');
INSERT INTO public.tb_equipment (status,value_hour_partner,value_hour_utfpr,value_sample_external,id,model,"name",short_name) VALUES (1,25,15,150,2,'431GC-210MS, Varian','Cromatógrafo a gás acoplado com espectrômetro de massa','CG-EM');
INSERT INTO public.tb_equipment (status,value_hour_partner,value_hour_utfpr,value_sample_external,id,model,"name",short_name) VALUES (1,22,14,80,3,'SDTQ600, TA Instruments','Analisador termogravimétrico','TGA-DTA-DSC');
INSERT INTO public.tb_equipment (status,value_sample_external,value_sample_partner,value_sample_utfpr,id,model,"name",short_name) VALUES (1,110,45,30,4,'Q20, TA instruments','Calorímetro diferencial exploratório','DSC');
INSERT INTO public.tb_equipment (status,value_hour_partner,value_hour_utfpr,value_sample_external,id,model,"name",short_name) VALUES (1,25,17,80,5,'TM3000, Hitachi','Microscópio Eletrônico de Varredura','MEV');
INSERT INTO public.tb_equipment (status,value_hour_partner,value_hour_utfpr,value_sample_external,id,model,"name",short_name) VALUES (1,45,25,105,6,'Miniflex 600, Rigaku','Difratômetro de Raios-X','DRX');
INSERT INTO public.tb_equipment (status,value_hour_partner,value_hour_utfpr,value_sample_external,id,model,"name",short_name) VALUES (1,5,2.50,15,7,'MPA, BRUKER','Espectrômetro de Infravermelho na região do próximo com Transformada de Fourier','FT-NIR');
INSERT INTO public.tb_equipment (status,value_hour_partner,value_hour_utfpr,value_sample_external,id,model,"name",short_name) VALUES (1,15,5,130,8,'Frontier, Perkin Elmer','Espectrômetro de Infravermelho na região do Médio com Transformada de Fourier','FT-MIR');
INSERT INTO public.tb_equipment (status,value_hour_partner,value_hour_utfpr,value_sample_external,id,model,"name",short_name) VALUES (1,15,8,70,9,'LAMBDA 45, Perkin Elmer','Espectrofotômetro de Absorção Molecular ','UV-Vis');
INSERT INTO public.tb_equipment (status,value_hour_partner,value_hour_utfpr,value_sample_external,id,model,"name",short_name) VALUES (1,30,18,80,10,'PinAAcle 900T, Perkin Elmer','Espectrômetro de Absorção Atômica','A ATÔMICA');
INSERT INTO public.tb_equipment (status,value_sample_external,value_sample_partner,value_sample_utfpr,id,model,"name",short_name) VALUES (1,140,60,40,11,'Euro EA, Euro Vector','Analisador elementar de CHNS-O','A ELEMENTAR');
