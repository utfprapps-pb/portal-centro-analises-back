-- INSERT INTO permission (description, action) values ('dash', 0);
-- INSERT INTO permission (description, action) values ('dash', 1);
-- INSERT INTO permission (description, action) values ('dash', 2);
-- INSERT INTO permission (description, action) values ('dash', 3);
-- INSERT INTO permission (description, action) values ('form', 0);
-- INSERT INTO permission (description, action) values ('form', 1);
-- INSERT INTO permission (description, action) values ('form', 2);
-- INSERT INTO permission (description, action) values ('form', 3);

INSERT INTO public.tb_user (id, balance, email_verified, "role", status, "type", created_at, partner_id, updated_at, cpf_cnpj, email, "name", "password", ra_siape) VALUES(-1, NULL, true, 0, 1, 0, '2024-04-09 19:00:13.407', NULL, '2024-04-09 19:20:12.952', '00000000000', 'lab@gmail.com', 'Laboratorio', '$2a$10$TZM4SIGTPefmOOTK1L9AMupCHuw7SgXp7BetKYJNBw2T8brnuybse', NULL);
INSERT INTO public.tb_user (id, balance, email_verified, "role", status, "type", created_at, partner_id, updated_at, cpf_cnpj, email, "name", "password", ra_siape) VALUES(-2, NULL, true, 1, 1, 0, '2024-04-09 19:00:13.407', NULL, '2024-04-09 19:20:12.952', '00000000001', 'professor@professor.com', 'Profssor', '$2a$10$TZM4SIGTPefmOOTK1L9AMupCHuw7SgXp7BetKYJNBw2T8brnuybse', NULL);
INSERT INTO public.tb_user (id, balance, email_verified, "role", status, "type", created_at, partner_id, updated_at, cpf_cnpj, email, "name", "password", ra_siape) VALUES(-3, NULL, true, 2, 1, 0, '2024-04-09 19:00:13.407', NULL, '2024-04-09 19:20:12.952', '00000000002', 'aluno@aluno.com', 'Aluno', '$2a$10$TZM4SIGTPefmOOTK1L9AMupCHuw7SgXp7BetKYJNBw2T8brnuybse', '1111111');

INSERT INTO public.tb_permission ("action", id, user_id, description) VALUES(0, 1, -1, 'Criação');
INSERT INTO public.tb_permission ("action", id, user_id, description) VALUES(1, 2, -1, 'Leitura');
INSERT INTO public.tb_permission ("action", id, user_id, description) VALUES(2, 3, -1, 'Atualização');
INSERT INTO public.tb_permission ("action", id, user_id, description) VALUES(3, 4, -1, 'Exclusão');
INSERT INTO public.tb_permission ("action", id, user_id, description) VALUES(1, 5, -2, 'Leitura');
INSERT INTO public.tb_permission ("action", id, user_id, description) VALUES(1, 6, -3, 'Leitura');

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
