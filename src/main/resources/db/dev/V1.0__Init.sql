-- DROP SEQUENCE public.tb_analysis_id_seq;

CREATE SEQUENCE public.tb_analysis_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_attachment_id_seq;

CREATE SEQUENCE public.tb_attachment_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_domain_role_id_seq;

CREATE SEQUENCE public.tb_domain_role_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_email_code_id_seq;

CREATE SEQUENCE public.tb_email_code_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_email_config_id_seq;

CREATE SEQUENCE public.tb_email_config_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_email_log_id_seq;

CREATE SEQUENCE public.tb_email_log_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_equipment_id_seq;

CREATE SEQUENCE public.tb_equipment_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_partner_id_seq;

CREATE SEQUENCE public.tb_partner_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_permission_id_seq;

CREATE SEQUENCE public.tb_permission_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_project_id_seq;

CREATE SEQUENCE public.tb_project_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_solicitation_amostra_analise_id_seq;

CREATE SEQUENCE public.tb_solicitation_amostra_analise_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_solicitation_amostra_foto_id_seq;

CREATE SEQUENCE public.tb_solicitation_amostra_foto_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_solicitation_amostra_id_seq;

CREATE SEQUENCE public.tb_solicitation_amostra_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_solicitation_form_id_seq;

CREATE SEQUENCE public.tb_solicitation_form_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_solicitation_historic_id_seq;

CREATE SEQUENCE public.tb_solicitation_historic_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_solicitation_id_seq;

CREATE SEQUENCE public.tb_solicitation_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_solicitation_termsofuse_id_seq;

CREATE SEQUENCE public.tb_solicitation_termsofuse_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_student_professor_id_seq;

CREATE SEQUENCE public.tb_student_professor_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_student_solicitation_id_seq;

CREATE SEQUENCE public.tb_student_solicitation_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_termsofuse_id_seq;

CREATE SEQUENCE public.tb_termsofuse_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_transaction_id_seq;

CREATE SEQUENCE public.tb_transaction_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE public.tb_user_id_seq;

CREATE SEQUENCE public.tb_user_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;-- public.tb_attachment definition

-- Drop table

-- DROP TABLE public.tb_attachment;

CREATE TABLE public.tb_attachment (
                                      id bigserial NOT NULL,
                                      bucket varchar(255) NULL,
                                      content_type varchar(255) NULL,
                                      created_at timestamp(6) NULL,
                                      file_hash varchar(255) NULL,
                                      file_name varchar(255) NULL,
                                      file_size int8 NULL,
                                      "index" int8 NULL,
                                      CONSTRAINT tb_attachment_pkey PRIMARY KEY (id)
);


-- public.tb_domain_role definition

-- Drop table

-- DROP TABLE public.tb_domain_role;

CREATE TABLE public.tb_domain_role (
                                       id bigserial NOT NULL,
                                       "domain" varchar(255) NULL,
                                       "role" int2 NULL,
                                       CONSTRAINT tb_domain_role_pkey PRIMARY KEY (id),
                                       CONSTRAINT tb_domain_role_role_check CHECK (((role >= 0) AND (role <= 4))),
                                       CONSTRAINT unique_domain_role UNIQUE (domain)
);


-- public.tb_email_config definition

-- Drop table

-- DROP TABLE public.tb_email_config;

CREATE TABLE public.tb_email_config (
                                        id bigserial NOT NULL,
                                        email varchar(255) NULL,
                                        host varchar(255) NULL,
                                        "password" varchar(255) NULL,
                                        port int4 NULL,
                                        CONSTRAINT tb_email_config_pkey PRIMARY KEY (id)
);


-- public.tb_email_log definition

-- Drop table

-- DROP TABLE public.tb_email_log;

CREATE TABLE public.tb_email_log (
                                     id bigserial NOT NULL,
                                     body text NULL,
                                     created_at timestamp(6) NULL,
                                     email_from varchar(255) NULL,
                                     email_to varchar(255) NULL,
                                     owner_ref varchar(255) NULL,
                                     status_email varchar(255) NULL,
                                     subject varchar(255) NULL,
                                     CONSTRAINT tb_email_log_pkey PRIMARY KEY (id),
                                     CONSTRAINT tb_email_log_status_email_check CHECK (((status_email)::text = ANY ((ARRAY['PROCESSING'::character varying, 'SENT'::character varying, 'ERROR'::character varying])::text[])))
);


-- public.tb_partner definition

-- Drop table

-- DROP TABLE public.tb_partner;

CREATE TABLE public.tb_partner (
                                   id bigserial NOT NULL,
                                   cnpj varchar(20) NOT NULL,
                                   "name" varchar(255) NULL,
                                   status int2 NULL,
                                   CONSTRAINT tb_partner_pkey PRIMARY KEY (id),
                                   CONSTRAINT tb_partner_status_check CHECK (((status >= 0) AND (status <= 1))),
                                   CONSTRAINT uk_qk5w0cu8f440ohlh1o5gps8sq UNIQUE (cnpj)
);


-- public.tb_user definition

-- Drop table

-- DROP TABLE public.tb_user;

CREATE TABLE public.tb_user (
                                id bigserial NOT NULL,
                                cpf_cnpj varchar(255) NOT NULL,
                                created_at timestamp(6) NULL,
                                email varchar(255) NOT NULL,
                                email_verified bool NULL,
                                "name" varchar(255) NOT NULL,
                                "password" varchar(255) NOT NULL,
                                ra_siape varchar(255) NULL,
                                "role" int2 NULL,
                                status int2 NULL,
                                "type" int2 NULL,
                                updated_at timestamp(6) NULL,
                                partner_id int8 NULL,
                                CONSTRAINT set_unique_email UNIQUE (email),
                                CONSTRAINT tb_user_pkey PRIMARY KEY (id),
                                CONSTRAINT tb_user_role_check CHECK (((role >= 0) AND (role <= 4))),
                                CONSTRAINT tb_user_status_check CHECK (((status >= 0) AND (status <= 1))),
                                CONSTRAINT tb_user_type_check CHECK (((type >= 0) AND (type <= 1))),
                                CONSTRAINT uk_mkqi074qrsh4olb9u7dug6cof UNIQUE (cpf_cnpj),
                                CONSTRAINT fkbqplyqefdhku3s1nop5h9r4i7 FOREIGN KEY (partner_id) REFERENCES public.tb_partner(id)
);


-- public.tb_email_code definition

-- Drop table

-- DROP TABLE public.tb_email_code;

CREATE TABLE public.tb_email_code (
                                      id bigserial NOT NULL,
                                      code varchar(255) NULL,
                                      created_at timestamp(6) NULL,
                                      validate_at timestamp(6) NULL,
                                      user_id int8 NULL,
                                      CONSTRAINT tb_email_code_pkey PRIMARY KEY (id),
                                      CONSTRAINT fkk5qogn023wep95dh3yww10uws FOREIGN KEY (user_id) REFERENCES public.tb_user(id)
);


-- public.tb_permission definition

-- Drop table

-- DROP TABLE public.tb_permission;

CREATE TABLE public.tb_permission (
                                      id bigserial NOT NULL,
                                      "action" int2 NULL,
                                      description varchar(50) NOT NULL,
                                      user_id int8 NULL,
                                      CONSTRAINT tb_permission_action_check CHECK (((action >= 0) AND (action <= 3))),
                                      CONSTRAINT tb_permission_pkey PRIMARY KEY (id),
                                      CONSTRAINT fkcdbrluhn9jgyn5de6fwr4fpv9 FOREIGN KEY (user_id) REFERENCES public.tb_user(id)
);


-- public.tb_project definition

-- Drop table

-- DROP TABLE public.tb_project;

CREATE TABLE public.tb_project (
                                   id bigserial NOT NULL,
                                   description varchar(255) NULL,
                                   other_project_nature varchar(255) NULL,
                                   project_nature varchar(255) NULL,
                                   subject varchar(255) NULL,
                                   user_id int8 NULL,
                                   CONSTRAINT tb_project_pkey PRIMARY KEY (id),
                                   CONSTRAINT tb_project_project_nature_check CHECK (((project_nature)::text = ANY ((ARRAY['MASTERS_THESIS'::character varying, 'DOCTORATE_DISSERTATION'::character varying, 'UNDERGRADUATE_THESIS'::character varying, 'INTERNSHIP_PROJECT'::character varying, 'SCIENTIFIC_INITIATION'::character varying, 'EXTENSION_PROJECT'::character varying, 'RESEARCH_PROJECT'::character varying, 'TEACHING_PROJECT'::character varying, 'PERSONAL_PROJECT'::character varying, 'OTHER'::character varying])::text[]))),
                                   CONSTRAINT fkmo8o99rcuh1bqkhh95uk226vw FOREIGN KEY (user_id) REFERENCES public.tb_user(id)
);


-- public.tb_project_student definition

-- Drop table

-- DROP TABLE public.tb_project_student;

CREATE TABLE public.tb_project_student (
                                           project_id int8 NOT NULL,
                                           user_id int8 NOT NULL,
                                           CONSTRAINT fkcwbb795soih5ptx4rypgbgwa1 FOREIGN KEY (project_id) REFERENCES public.tb_project(id),
                                           CONSTRAINT fknmq5ymcflrq4u9gjv3udjum3g FOREIGN KEY (user_id) REFERENCES public.tb_user(id)
);


-- public.tb_student_professor definition

-- Drop table

-- DROP TABLE public.tb_student_professor;

CREATE TABLE public.tb_student_professor (
                                             id bigserial NOT NULL,
                                             approved int2 NULL,
                                             created_at date NULL,
                                             professor int8 NOT NULL,
                                             student int8 NOT NULL,
                                             CONSTRAINT tb_student_professor_approved_check CHECK (((approved >= 0) AND (approved <= 2))),
                                             CONSTRAINT tb_student_professor_pkey PRIMARY KEY (id),
                                             CONSTRAINT fkhlsj4g775lmodv0qvpst8f2uf FOREIGN KEY (student) REFERENCES public.tb_user(id),
                                             CONSTRAINT fkru5xjbhqyjmv7k70g6ifvidjy FOREIGN KEY (professor) REFERENCES public.tb_user(id)
);


-- public.tb_student_solicitation definition

-- Drop table

-- DROP TABLE public.tb_student_solicitation;

CREATE TABLE public.tb_student_solicitation (
                                                id bigserial NOT NULL,
                                                creation_date timestamp(6) NULL,
                                                finish_date timestamp(6) NULL,
                                                status int2 NULL,
                                                finished_by int8 NULL,
                                                solicited_by int8 NULL,
                                                solicited_to int8 NULL,
                                                CONSTRAINT tb_student_solicitation_pkey PRIMARY KEY (id),
                                                CONSTRAINT tb_student_solicitation_status_check CHECK (((status >= 0) AND (status <= 4))),
                                                CONSTRAINT fkkaic9wktv2tiy87bdabvd23d9 FOREIGN KEY (solicited_by) REFERENCES public.tb_user(id),
                                                CONSTRAINT fklliw0yj8olqhyh9prpdyn4d6y FOREIGN KEY (finished_by) REFERENCES public.tb_user(id),
                                                CONSTRAINT fkr6hialw3bht2y59gqkhbt9481 FOREIGN KEY (solicited_to) REFERENCES public.tb_user(id)
);


-- public.tb_termsofuse definition

-- Drop table

-- DROP TABLE public.tb_termsofuse;

CREATE TABLE public.tb_termsofuse (
                                      id bigserial NOT NULL,
                                      created_at timestamp(6) NULL,
                                      updated_at timestamp(6) NULL,
                                      description text NULL,
                                      form text NULL,
                                      solicitation_type varchar(255) NULL,
                                      status int2 NULL,
                                      title varchar(255) NULL,
                                      created_by int8 NULL,
                                      updated_by int8 NULL,
                                      CONSTRAINT tb_termsofuse_pkey PRIMARY KEY (id),
                                      CONSTRAINT tb_termsofuse_solicitation_type_check CHECK (((solicitation_type)::text = ANY ((ARRAY['AA'::character varying, 'AW'::character varying, 'CLAE'::character varying, 'COR'::character varying, 'DRX'::character varying, 'DSC'::character varying, 'FTMIR'::character varying, 'MEV'::character varying, 'TGADTA'::character varying])::text[]))),
                                      CONSTRAINT tb_termsofuse_status_check CHECK (((status >= 0) AND (status <= 1))),
                                      CONSTRAINT fkg7deguu9sraouj2cqej8qece3 FOREIGN KEY (updated_by) REFERENCES public.tb_user(id),
                                      CONSTRAINT fkko1vl91822vk421ftgtkq4kmq FOREIGN KEY (created_by) REFERENCES public.tb_user(id)
);


-- public.tb_analysis definition

-- Drop table

-- DROP TABLE public.tb_analysis;

CREATE TABLE public.tb_analysis (
                                    id bigserial NOT NULL,
                                    description varchar(255) NULL,
                                    "name" varchar(255) NULL,
                                    equipment_id int8 NULL,
                                    CONSTRAINT tb_analysis_pkey PRIMARY KEY (id)
);


-- public.tb_equipment definition

-- Drop table

-- DROP TABLE public.tb_equipment;

CREATE TABLE public.tb_equipment (
                                     id bigserial NOT NULL,
                                     model varchar(255) NULL,
                                     "name" varchar(255) NULL,
                                     short_name varchar(255) NULL,
                                     status int2 NULL,
                                     value_hour_external numeric(38, 2) NULL,
                                     value_hour_partner numeric(38, 2) NULL,
                                     value_hour_utfpr numeric(38, 2) NULL,
                                     value_sample_external numeric(38, 2) NULL,
                                     value_sample_partner numeric(38, 2) NULL,
                                     value_sample_utfpr numeric(38, 2) NULL,
                                     analise_id int8 NULL,
                                     CONSTRAINT tb_equipment_pkey PRIMARY KEY (id),
                                     CONSTRAINT tb_equipment_status_check CHECK (((status >= 0) AND (status <= 1)))
);


-- public.tb_solicitation definition

-- Drop table

-- DROP TABLE public.tb_solicitation;

CREATE TABLE public.tb_solicitation (
                                        id bigserial NOT NULL,
                                        created_at timestamp(6) NULL,
                                        updated_at timestamp(6) NULL,
                                        amount_hours numeric(38, 2) NULL,
                                        amount_samples int4 NULL,
                                        observation varchar(255) NULL,
                                        other_project_nature varchar(255) NULL,
                                        paid bool NULL,
                                        price numeric(38, 2) NULL,
                                        project_nature varchar(255) NULL,
                                        schedule_date timestamp(6) NULL,
                                        solicitation_type varchar(255) NULL,
                                        status int2 NULL,
                                        total_price numeric(38, 2) NULL,
                                        created_by int8 NULL,
                                        updated_by int8 NULL,
                                        form_id int8 NULL,
                                        project_id int8 NULL,
                                        responsavel_id int8 NULL,
                                        CONSTRAINT tb_solicitation_pkey PRIMARY KEY (id),
                                        CONSTRAINT tb_solicitation_project_nature_check CHECK (((project_nature)::text = ANY ((ARRAY['MASTERS_THESIS'::character varying, 'DOCTORATE_DISSERTATION'::character varying, 'UNDERGRADUATE_THESIS'::character varying, 'INTERNSHIP_PROJECT'::character varying, 'SCIENTIFIC_INITIATION'::character varying, 'EXTENSION_PROJECT'::character varying, 'RESEARCH_PROJECT'::character varying, 'TEACHING_PROJECT'::character varying, 'PERSONAL_PROJECT'::character varying, 'OTHER'::character varying])::text[]))),
                                        CONSTRAINT tb_solicitation_solicitation_type_check CHECK (((solicitation_type)::text = ANY ((ARRAY['AA'::character varying, 'AW'::character varying, 'CLAE'::character varying, 'COR'::character varying, 'DRX'::character varying, 'DSC'::character varying, 'FTMIR'::character varying, 'MEV'::character varying, 'TGADTA'::character varying])::text[]))),
                                        CONSTRAINT tb_solicitation_status_check CHECK (((status >= 0) AND (status <= 10)))
);


-- public.tb_solicitation_amostra definition

-- Drop table

-- DROP TABLE public.tb_solicitation_amostra;

CREATE TABLE public.tb_solicitation_amostra (
                                                id bigserial NOT NULL,
                                                atmosfera varchar(255) NULL,
                                                composicao varchar(255) NULL,
                                                concluida bool NULL,
                                                descarte_inorganico varchar(255) NULL,
                                                descarte_inorganico_outro varchar(255) NULL,
                                                descarte_organico varchar(255) NULL,
                                                descarte_organico_outro varchar(255) NULL,
                                                descarte_usuario varchar(255) NULL,
                                                descarte_usuario_outro varchar(255) NULL,
                                                description varchar(255) NULL,
                                                expande bool NULL,
                                                faixa_varredura int8 NULL,
                                                fluxo_gas int8 NULL,
                                                gas_liberado varchar(255) NULL,
                                                identification varchar(255) NULL,
                                                "index" int4 NULL,
                                                intervalo_temperatura numeric(38, 2) NULL,
                                                leituras int8 NULL,
                                                libera_gas int2 NULL,
                                                massa int8 NULL,
                                                natureza_amostra varchar(255) NULL,
                                                objetivo varchar(255) NULL,
                                                step int2 NULL,
                                                taxa_aquecimento numeric(38, 2) NULL,
                                                tecnica varchar(255) NULL,
                                                tempo_passo int8 NULL,
                                                tipo_amostra varchar(255) NULL,
                                                toxic varchar(255) NULL,
                                                velocidade_varredura int8 NULL,
                                                form_id int8 NULL,
                                                modelo_microscopia int8 NULL,
                                                CONSTRAINT tb_solicitation_amostra_libera_gas_check CHECK (((libera_gas >= 0) AND (libera_gas <= 1))),
                                                CONSTRAINT tb_solicitation_amostra_pkey PRIMARY KEY (id),
                                                CONSTRAINT tb_solicitation_amostra_step_check CHECK (((step >= 0) AND (step <= 1)))
);


-- public.tb_solicitation_amostra_analise definition

-- Drop table

-- DROP TABLE public.tb_solicitation_amostra_analise;

CREATE TABLE public.tb_solicitation_amostra_analise (
                                                        id bigserial NOT NULL,
                                                        datafin timestamp(6) NULL,
                                                        dataini timestamp(6) NULL,
                                                        amostra_id int8 NULL,
                                                        equipment_id int8 NULL,
                                                        CONSTRAINT tb_solicitation_amostra_analise_pkey PRIMARY KEY (id)
);


-- public.tb_solicitation_amostra_foto definition

-- Drop table

-- DROP TABLE public.tb_solicitation_amostra_foto;

CREATE TABLE public.tb_solicitation_amostra_foto (
                                                     id bigserial NOT NULL,
                                                     aproximacoes int8 NULL,
                                                     qtd_fotos_aproximacao int8 NULL,
                                                     amostra_id int8 NULL,
                                                     CONSTRAINT tb_solicitation_amostra_foto_pkey PRIMARY KEY (id)
);


-- public.tb_solicitation_amostra_fotos definition

-- Drop table

-- DROP TABLE public.tb_solicitation_amostra_fotos;

CREATE TABLE public.tb_solicitation_amostra_fotos (
                                                      tb_solicitation_amostra_id int8 NOT NULL,
                                                      fotos_id int8 NOT NULL,
                                                      CONSTRAINT uk_o26ldxntnfn4pc4auv4yi1eu0 UNIQUE (fotos_id)
);


-- public.tb_solicitation_form definition

-- Drop table

-- DROP TABLE public.tb_solicitation_form;

CREATE TABLE public.tb_solicitation_form (
                                             id bigserial NOT NULL,
                                             caracteristica int2 NULL,
                                             citacao bool NULL,
                                             coluna varchar(255) NULL,
                                             comp_onda_canal_1 numeric(38, 2) NULL,
                                             comp_onda_canal_2 numeric(38, 2) NULL,
                                             composicao_fase_movel varchar(255) NULL,
                                             condicoes_gradiente varchar(255) NULL,
                                             curva_concentracao varchar(255) NULL,
                                             elementos varchar(255) NULL,
                                             faixa_varredura int8 NULL,
                                             fluxo numeric(38, 2) NULL,
                                             forno int2 NULL,
                                             limites_concentracao varchar(255) NULL,
                                             location_med varchar(255) NULL,
                                             methodology_description varchar(255) NULL,
                                             modo_analise int2 NULL,
                                             modo_eluicao int2 NULL,
                                             registros_espectos int2 NULL,
                                             resolucao int8 NULL,
                                             retirada int2 NULL,
                                             servico int2 NULL,
                                             solvente varchar(255) NULL,
                                             temperatura_forno_coluna numeric(38, 2) NULL,
                                             tempo_analise numeric(38, 2) NULL,
                                             tipo_leitura int2 NULL,
                                             utilizapda bool NULL,
                                             volume_injetado numeric(38, 2) NULL,
                                             solicitation_id int8 NULL,
                                             CONSTRAINT tb_solicitation_form_caracteristica_check CHECK (((caracteristica >= 0) AND (caracteristica <= 1))),
                                             CONSTRAINT tb_solicitation_form_forno_check CHECK (((forno >= 0) AND (forno <= 2))),
                                             CONSTRAINT tb_solicitation_form_modo_analise_check CHECK (((modo_analise >= 0) AND (modo_analise <= 1))),
                                             CONSTRAINT tb_solicitation_form_modo_eluicao_check CHECK (((modo_eluicao >= 0) AND (modo_eluicao <= 1))),
                                             CONSTRAINT tb_solicitation_form_pkey PRIMARY KEY (id),
                                             CONSTRAINT tb_solicitation_form_registros_espectos_check CHECK (((registros_espectos >= 0) AND (registros_espectos <= 1))),
                                             CONSTRAINT tb_solicitation_form_retirada_check CHECK (((retirada >= 0) AND (retirada <= 1))),
                                             CONSTRAINT tb_solicitation_form_servico_check CHECK (((servico >= 0) AND (servico <= 2))),
                                             CONSTRAINT tb_solicitation_form_tipo_leitura_check CHECK (((tipo_leitura >= 0) AND (tipo_leitura <= 1)))
);


-- public.tb_solicitation_historic definition

-- Drop table

-- DROP TABLE public.tb_solicitation_historic;

CREATE TABLE public.tb_solicitation_historic (
                                                 id bigserial NOT NULL,
                                                 created_at timestamp(6) NULL,
                                                 observation varchar(255) NULL,
                                                 status int2 NULL,
                                                 created_by int8 NULL,
                                                 solicitation_id int8 NULL,
                                                 CONSTRAINT tb_solicitation_historic_pkey PRIMARY KEY (id),
                                                 CONSTRAINT tb_solicitation_historic_status_check CHECK (((status >= 0) AND (status <= 10)))
);


-- public.tb_solicitation_termsofuse definition

-- Drop table

-- DROP TABLE public.tb_solicitation_termsofuse;

CREATE TABLE public.tb_solicitation_termsofuse (
                                                   id bigserial NOT NULL,
                                                   accepted bool NOT NULL,
                                                   solicitation_id int8 NULL,
                                                   termsofuse_id int8 NULL,
                                                   CONSTRAINT tb_solicitation_termsofuse_pkey PRIMARY KEY (id)
);


-- public.tb_transaction definition

-- Drop table

-- DROP TABLE public.tb_transaction;

CREATE TABLE public.tb_transaction (
                                       id bigserial NOT NULL,
                                       created_at timestamp(6) NULL,
                                       updated_at timestamp(6) NULL,
                                       description varchar(255) NULL,
                                       total_value numeric(38, 2) NULL,
                                       created_by int8 NULL,
                                       updated_by int8 NULL,
                                       solicitation_id int8 NULL,
                                       user_id int8 NULL,
                                       CONSTRAINT tb_transaction_pkey PRIMARY KEY (id)
);


-- public.tb_analysis foreign keys

ALTER TABLE public.tb_analysis ADD CONSTRAINT fk942vrnty41a31q2jcv4xvgdac FOREIGN KEY (equipment_id) REFERENCES public.tb_equipment(id);


-- public.tb_equipment foreign keys

ALTER TABLE public.tb_equipment ADD CONSTRAINT fkout934n68pnnett2o309f42i2 FOREIGN KEY (analise_id) REFERENCES public.tb_solicitation_amostra_analise(id);


-- public.tb_solicitation foreign keys

ALTER TABLE public.tb_solicitation ADD CONSTRAINT fk88ofphj28kn58vh151i2nuium FOREIGN KEY (responsavel_id) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_solicitation ADD CONSTRAINT fkajrki8u7sfi8nx9qtbh3abu2t FOREIGN KEY (form_id) REFERENCES public.tb_solicitation_form(id);
ALTER TABLE public.tb_solicitation ADD CONSTRAINT fkdw5mecywrq858t9qvcv4u4slb FOREIGN KEY (project_id) REFERENCES public.tb_project(id);
ALTER TABLE public.tb_solicitation ADD CONSTRAINT fkgo2oerqvcl4y85vnfgxgntlme FOREIGN KEY (updated_by) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_solicitation ADD CONSTRAINT fkt2m8qs77gnaj2gwj5c8qycsif FOREIGN KEY (created_by) REFERENCES public.tb_user(id);


-- public.tb_solicitation_amostra foreign keys

ALTER TABLE public.tb_solicitation_amostra ADD CONSTRAINT fkr3bu7innmrue7wdlq1vvh1mrm FOREIGN KEY (form_id) REFERENCES public.tb_solicitation_form(id);
ALTER TABLE public.tb_solicitation_amostra ADD CONSTRAINT fkskr0f5frcxwpau5v3pmxhblh1 FOREIGN KEY (modelo_microscopia) REFERENCES public.tb_attachment(id);


-- public.tb_solicitation_amostra_analise foreign keys

ALTER TABLE public.tb_solicitation_amostra_analise ADD CONSTRAINT fkjlculk2nx52668ke53pdcexri FOREIGN KEY (amostra_id) REFERENCES public.tb_solicitation_amostra(id);
ALTER TABLE public.tb_solicitation_amostra_analise ADD CONSTRAINT fkqmxens969dyqujn0yq9hba366 FOREIGN KEY (equipment_id) REFERENCES public.tb_equipment(id);


-- public.tb_solicitation_amostra_foto foreign keys

ALTER TABLE public.tb_solicitation_amostra_foto ADD CONSTRAINT fka49vxoqftbw5fpk1avxkccxk0 FOREIGN KEY (amostra_id) REFERENCES public.tb_solicitation_amostra(id);


-- public.tb_solicitation_amostra_fotos foreign keys

ALTER TABLE public.tb_solicitation_amostra_fotos ADD CONSTRAINT fknsechu18jmle5i5tgb7599ecv FOREIGN KEY (tb_solicitation_amostra_id) REFERENCES public.tb_solicitation_amostra(id);
ALTER TABLE public.tb_solicitation_amostra_fotos ADD CONSTRAINT fkr5d7lx7v7x295hfmenapj0q7m FOREIGN KEY (fotos_id) REFERENCES public.tb_solicitation_amostra_foto(id);


-- public.tb_solicitation_form foreign keys

ALTER TABLE public.tb_solicitation_form ADD CONSTRAINT fkdh4rs92rqua7wmf8emmurogd0 FOREIGN KEY (solicitation_id) REFERENCES public.tb_solicitation(id);


-- public.tb_solicitation_historic foreign keys

ALTER TABLE public.tb_solicitation_historic ADD CONSTRAINT fk52gm4wvo3ye32xunl257t3pyt FOREIGN KEY (solicitation_id) REFERENCES public.tb_solicitation(id);
ALTER TABLE public.tb_solicitation_historic ADD CONSTRAINT fkeh8rihsb9w5qorvwq53qt2yg FOREIGN KEY (created_by) REFERENCES public.tb_user(id);


-- public.tb_solicitation_termsofuse foreign keys

ALTER TABLE public.tb_solicitation_termsofuse ADD CONSTRAINT fk8gs71h5stnt0s363665opatue FOREIGN KEY (solicitation_id) REFERENCES public.tb_solicitation(id);
ALTER TABLE public.tb_solicitation_termsofuse ADD CONSTRAINT fkqfhyhtwmewgur995apenhdv1o FOREIGN KEY (termsofuse_id) REFERENCES public.tb_termsofuse(id);


-- public.tb_transaction foreign keys

ALTER TABLE public.tb_transaction ADD CONSTRAINT fk5dd7p67lhqlc7mequnuuye60p FOREIGN KEY (updated_by) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_transaction ADD CONSTRAINT fk7f3wtdpf61kpwpm1p7ygh2ccf FOREIGN KEY (user_id) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_transaction ADD CONSTRAINT fkjb5qnoog1c3py5smh9v0a5kk4 FOREIGN KEY (created_by) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_transaction ADD CONSTRAINT fkjsj1mnycfll8c6aslewlt7j7o FOREIGN KEY (solicitation_id) REFERENCES public.tb_solicitation(id);