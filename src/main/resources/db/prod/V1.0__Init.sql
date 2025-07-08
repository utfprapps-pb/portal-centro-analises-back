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

-- DROP SEQUENCE public.tb_finance_detail_id_seq;
CREATE SEQUENCE public.tb_finance_detail_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

-- DROP SEQUENCE public.tb_finance_id_seq;
CREATE SEQUENCE public.tb_finance_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

-- DROP SEQUENCE public.tb_finance_transaction_id_seq;
CREATE SEQUENCE public.tb_finance_transaction_id_seq
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

-- DROP SEQUENCE public.tb_project_student_id_seq;
CREATE SEQUENCE public.tb_project_student_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

-- DROP SEQUENCE public.tb_solicitation_form_gradient_id_seq;
CREATE SEQUENCE public.tb_solicitation_form_gradient_id_seq
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

-- DROP SEQUENCE public.tb_solicitation_sample_analysis_id_seq;
CREATE SEQUENCE public.tb_solicitation_sample_analysis_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

-- DROP SEQUENCE public.tb_solicitation_sample_id_seq;
CREATE SEQUENCE public.tb_solicitation_sample_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

-- DROP SEQUENCE public.tb_solicitation_sample_photo_id_seq;
CREATE SEQUENCE public.tb_solicitation_sample_photo_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

-- DROP SEQUENCE public.tb_solicitation_terms_of_use_id_seq;
CREATE SEQUENCE public.tb_solicitation_terms_of_use_id_seq
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

-- DROP SEQUENCE public.tb_terms_of_use_id_seq;
CREATE SEQUENCE public.tb_terms_of_use_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

-- DROP SEQUENCE public.tb_user_balance_id_seq;
CREATE SEQUENCE public.tb_user_balance_id_seq
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
    NO CYCLE;

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

-- public.tb_user_balance definition
-- Drop table
-- DROP TABLE public.tb_user_balance;
CREATE TABLE public.tb_user_balance (
                                        id bigserial NOT NULL,
                                        balance numeric(38, 2) NULL,
                                        negative_limit numeric(38, 2) NULL,
                                        user_id int8 NULL,
                                        CONSTRAINT set_unique_user UNIQUE (user_id),
                                        CONSTRAINT tb_user_balance_pkey PRIMARY KEY (id),
                                        CONSTRAINT fkiwb30tdywc1nm0poihqhmg520 FOREIGN KEY (user_id) REFERENCES public.tb_user(id)
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
                                           id bigserial NOT NULL,
                                           project_id int8 NULL,
                                           user_id int8 NOT NULL,
                                           CONSTRAINT tb_project_student_pkey PRIMARY KEY (id),
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

-- public.tb_terms_of_use definition
-- Drop table
-- DROP TABLE public.tb_terms_of_use;
CREATE TABLE public.tb_terms_of_use (
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
                                        CONSTRAINT tb_terms_of_use_pkey PRIMARY KEY (id),
                                        CONSTRAINT tb_terms_of_use_solicitation_type_check CHECK (((solicitation_type)::text = ANY ((ARRAY['AA'::character varying, 'AW'::character varying, 'CLAE'::character varying, 'COR'::character varying, 'DRX'::character varying, 'DSC'::character varying, 'FTMIR'::character varying, 'MEV'::character varying, 'TGADTA'::character varying])::text[]))),
                                        CONSTRAINT tb_terms_of_use_status_check CHECK (((status >= 0) AND (status <= 1))),
                                        CONSTRAINT fk4lff884odvyojrtw3p3yys276 FOREIGN KEY (created_by) REFERENCES public.tb_user(id),
                                        CONSTRAINT fkh0n5jyiq93tuftg267fdl31af FOREIGN KEY (updated_by) REFERENCES public.tb_user(id)
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

-- public.tb_attachment definition
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
                                      finance_id int8 NULL,
                                      CONSTRAINT tb_attachment_pkey PRIMARY KEY (id)
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
                                     analysis_id int8 NULL,
                                     CONSTRAINT tb_equipment_pkey PRIMARY KEY (id),
                                     CONSTRAINT tb_equipment_status_check CHECK (((status >= 0) AND (status <= 1)))
);

-- public.tb_finance definition
-- Drop table
-- DROP TABLE public.tb_finance;
CREATE TABLE public.tb_finance (
                                   id bigserial NOT NULL,
                                   created_at timestamp(6) NULL,
                                   updated_at timestamp(6) NULL,
                                   update_balance bool NULL,
                                   description varchar(255) NULL,
                                   due_date timestamp(6) NULL,
                                   observation varchar(255) NULL,
                                   state int2 NULL,
                                   value numeric(38, 2) NULL,
                                   created_by int8 NULL,
                                   updated_by int8 NULL,
                                   payer_id int8 NULL,
                                   responsible_id int8 NULL,
                                   solicitation_id int8 NULL,
                                   CONSTRAINT tb_finance_pkey PRIMARY KEY (id),
                                   CONSTRAINT tb_finance_state_check CHECK (((state >= 0) AND (state <= 2)))
);

-- public.tb_finance_detail definition
-- Drop table
-- DROP TABLE public.tb_finance_detail;
CREATE TABLE public.tb_finance_detail (
                                          id bigserial NOT NULL,
                                          created_at timestamp(6) NULL,
                                          updated_at timestamp(6) NULL,
                                          calculatebyhours bool NULL,
                                          quantity numeric(38, 2) NULL,
                                          value numeric(38, 2) NULL,
                                          created_by int8 NULL,
                                          updated_by int8 NULL,
                                          equipment_id int8 NULL,
                                          finance_id int8 NULL,
                                          CONSTRAINT tb_finance_detail_pkey PRIMARY KEY (id)
);

-- public.tb_finance_transaction definition
-- Drop table
-- DROP TABLE public.tb_finance_transaction;
CREATE TABLE public.tb_finance_transaction (
                                               id bigserial NOT NULL,
                                               value numeric(38, 2) NULL,
                                               finance_id int8 NULL,
                                               user_id int8 NULL,
                                               CONSTRAINT tb_finance_transaction_pkey PRIMARY KEY (id)
);

-- public.tb_solicitation definition
-- Drop table
-- DROP TABLE public.tb_solicitation;
CREATE TABLE public.tb_solicitation (
                                        id bigserial NOT NULL,
                                        created_at timestamp(6) NULL,
                                        updated_at timestamp(6) NULL,
                                        amount_samples int4 NULL,
                                        observation varchar(255) NULL,
                                        other_project_nature varchar(255) NULL,
                                        project_nature varchar(255) NULL,
                                        solicitation_type varchar(255) NULL,
                                        status int2 NULL,
                                        created_by int8 NULL,
                                        updated_by int8 NULL,
                                        form_id int8 NULL,
                                        project_id int8 NULL,
                                        responsible_id int8 NULL,
                                        CONSTRAINT tb_solicitation_pkey PRIMARY KEY (id),
                                        CONSTRAINT tb_solicitation_project_nature_check CHECK (((project_nature)::text = ANY ((ARRAY['MASTERS_THESIS'::character varying, 'DOCTORATE_DISSERTATION'::character varying, 'UNDERGRADUATE_THESIS'::character varying, 'INTERNSHIP_PROJECT'::character varying, 'SCIENTIFIC_INITIATION'::character varying, 'EXTENSION_PROJECT'::character varying, 'RESEARCH_PROJECT'::character varying, 'TEACHING_PROJECT'::character varying, 'PERSONAL_PROJECT'::character varying, 'OTHER'::character varying])::text[]))),
                                        CONSTRAINT tb_solicitation_solicitation_type_check CHECK (((solicitation_type)::text = ANY ((ARRAY['AA'::character varying, 'AW'::character varying, 'CLAE'::character varying, 'COR'::character varying, 'DRX'::character varying, 'DSC'::character varying, 'FTMIR'::character varying, 'MEV'::character varying, 'TGADTA'::character varying])::text[]))),
                                        CONSTRAINT tb_solicitation_status_check CHECK (((status >= 0) AND (status <= 10)))
);

-- public.tb_solicitation_form definition
-- Drop table
-- DROP TABLE public.tb_solicitation_form;
CREATE TABLE public.tb_solicitation_form (
                                             id bigserial NOT NULL,
                                             characteristic int2 NULL,
                                             citation bool NULL,
                                             column_data varchar(255) NULL,
                                             wave_length_channel_1 numeric(38, 2) NULL,
                                             wave_length_channel_2 numeric(38, 2) NULL,
                                             mobile_phase_composition varchar(255) NULL,
                                             concentration_curve varchar(255) NULL,
                                             elements varchar(255) NULL,
                                             scan_range varchar(255) NULL,
                                             flow numeric(38, 2) NULL,
                                             oven int2 NULL,
                                             concentration_limits varchar(255) NULL,
                                             location_med varchar(255) NULL,
                                             methodology_description varchar(255) NULL,
                                             analysis_mode int2 NULL,
                                             elution_mode int2 NULL,
                                             spectrum_records int2 NULL,
                                             resolution int8 NULL,
                                             pickup int2 NULL,
                                             scans int8 NULL,
                                             service int2 NULL,
                                             solvent varchar(255) NULL,
                                             column_oven_temperature numeric(38, 2) NULL,
                                             analysis_time numeric(38, 2) NULL,
                                             reading_type int2 NULL,
                                             uses_pda bool NULL,
                                             injected_volume numeric(38, 2) NULL,
                                             solicitation_id int8 NULL,
                                             CONSTRAINT tb_solicitation_form_analysis_mode_check CHECK (((analysis_mode >= 0) AND (analysis_mode <= 1))),
                                             CONSTRAINT tb_solicitation_form_characteristic_check CHECK (((characteristic >= 0) AND (characteristic <= 1))),
                                             CONSTRAINT tb_solicitation_form_elution_mode_check CHECK (((elution_mode >= 0) AND (elution_mode <= 1))),
                                             CONSTRAINT tb_solicitation_form_oven_check CHECK (((oven >= 0) AND (oven <= 2))),
                                             CONSTRAINT tb_solicitation_form_pickup_check CHECK (((pickup >= 0) AND (pickup <= 1))),
                                             CONSTRAINT tb_solicitation_form_pkey PRIMARY KEY (id),
                                             CONSTRAINT tb_solicitation_form_reading_type_check CHECK (((reading_type >= 0) AND (reading_type <= 1))),
                                             CONSTRAINT tb_solicitation_form_service_check CHECK (((service >= 0) AND (service <= 2))),
                                             CONSTRAINT tb_solicitation_form_spectrum_records_check CHECK (((spectrum_records >= 0) AND (spectrum_records <= 1)))
);

-- public.tb_solicitation_form_gradient definition
-- Drop table
-- DROP TABLE public.tb_solicitation_form_gradient;
CREATE TABLE public.tb_solicitation_form_gradient (
                                                      id bigserial NOT NULL,
                                                      flow numeric(38, 2) NULL,
                                                      "index" int4 NULL,
                                                      solvent_a numeric(38, 2) NULL,
                                                      solvent_b numeric(38, 2) NULL,
                                                      "time" numeric(38, 2) NULL,
                                                      form_id int8 NULL,
                                                      CONSTRAINT tb_solicitation_form_gradient_pkey PRIMARY KEY (id)
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

-- public.tb_solicitation_sample definition
-- Drop table
-- DROP TABLE public.tb_solicitation_sample;
CREATE TABLE public.tb_solicitation_sample (
                                               id bigserial NOT NULL,
                                               atmosphere varchar(255) NULL,
                                               composition varchar(255) NULL,
                                               completed bool NULL,
                                               inorganic_disposal varchar(255) NULL,
                                               inorganic_disposal_other varchar(255) NULL,
                                               organic_disposal varchar(255) NULL,
                                               organic_disposal_other varchar(255) NULL,
                                               user_disposal varchar(255) NULL,
                                               user_disposal_other varchar(255) NULL,
                                               description varchar(255) NULL,
                                               expand bool NULL,
                                               scan_range int8 NULL,
                                               gas_flow int8 NULL,
                                               released_gas varchar(255) NULL,
                                               identification varchar(255) NULL,
                                               "index" int4 NULL,
                                               temperature_range varchar(255) NULL,
                                               readings int8 NULL,
                                               gas_release int2 NULL,
                                               mass int8 NULL,
                                               sample_nature varchar(255) NULL,
                                               objective varchar(255) NULL,
                                               step int2 NULL,
                                               heating_rate numeric(38, 2) NULL,
                                               technique varchar(255) NULL,
                                               step_time int8 NULL,
                                               sample_type varchar(255) NULL,
                                               toxic varchar(255) NULL,
                                               scan_speed int8 NULL,
                                               form_id int8 NULL,
                                               microscopy_model int8 NULL,
                                               CONSTRAINT tb_solicitation_sample_gas_release_check CHECK (((gas_release >= 0) AND (gas_release <= 1))),
                                               CONSTRAINT tb_solicitation_sample_pkey PRIMARY KEY (id),
                                               CONSTRAINT tb_solicitation_sample_step_check CHECK (((step >= 0) AND (step <= 1)))
);

-- public.tb_solicitation_sample_analysis definition
-- Drop table
-- DROP TABLE public.tb_solicitation_sample_analysis;
CREATE TABLE public.tb_solicitation_sample_analysis (
                                                        id bigserial NOT NULL,
                                                        end_date timestamp(6) NULL,
                                                        start_date timestamp(6) NULL,
                                                        sample_id int8 NULL,
                                                        equipment_id int8 NULL,
                                                        CONSTRAINT tb_solicitation_sample_analysis_pkey PRIMARY KEY (id)
);

-- public.tb_solicitation_sample_fotos definition
-- Drop table
-- DROP TABLE public.tb_solicitation_sample_fotos;
CREATE TABLE public.tb_solicitation_sample_fotos (
                                                     tb_solicitation_sample_id int8 NOT NULL,
                                                     fotos_id int8 NOT NULL,
                                                     CONSTRAINT uk_kqkjnk251vx3eqkhlfrnp87ms UNIQUE (fotos_id)
);

-- public.tb_solicitation_sample_photo definition
-- Drop table
-- DROP TABLE public.tb_solicitation_sample_photo;
CREATE TABLE public.tb_solicitation_sample_photo (
                                                     id bigserial NOT NULL,
                                                     approximations int8 NULL,
                                                     total_approximations_photos int8 NULL,
                                                     sample_id int8 NULL,
                                                     CONSTRAINT tb_solicitation_sample_photo_pkey PRIMARY KEY (id)
);

-- public.tb_solicitation_terms_of_use definition
-- Drop table
-- DROP TABLE public.tb_solicitation_terms_of_use;
CREATE TABLE public.tb_solicitation_terms_of_use (
                                                     id bigserial NOT NULL,
                                                     accepted bool NOT NULL,
                                                     solicitation_id int8 NULL,
                                                     terms_of_use_id int8 NULL,
                                                     CONSTRAINT tb_solicitation_terms_of_use_pkey PRIMARY KEY (id)
);

-- public.tb_analysis foreign keys
ALTER TABLE public.tb_analysis ADD CONSTRAINT fk942vrnty41a31q2jcv4xvgdac FOREIGN KEY (equipment_id) REFERENCES public.tb_equipment(id);

-- public.tb_attachment foreign keys
ALTER TABLE public.tb_attachment ADD CONSTRAINT fkor7o0gb3044gqjqt278eoay12 FOREIGN KEY (finance_id) REFERENCES public.tb_finance(id);

-- public.tb_equipment foreign keys
ALTER TABLE public.tb_equipment ADD CONSTRAINT fkth8vsurjx7pi6kth645uuoj7h FOREIGN KEY (analysis_id) REFERENCES public.tb_solicitation_sample_analysis(id);

-- public.tb_finance foreign keys
ALTER TABLE public.tb_finance ADD CONSTRAINT fk1yxdl4y7sby9y08r9dmj2p4b6 FOREIGN KEY (solicitation_id) REFERENCES public.tb_solicitation(id);
ALTER TABLE public.tb_finance ADD CONSTRAINT fk32j7jkju5iolx5qesra9935kc FOREIGN KEY (updated_by) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_finance ADD CONSTRAINT fkcc4ei5c38sr9td17vgnmcam8u FOREIGN KEY (created_by) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_finance ADD CONSTRAINT fkisesn0ein4ypc0s3gpcdfgpjs FOREIGN KEY (payer_id) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_finance ADD CONSTRAINT fkj3jmex3easn66n5221umqx419 FOREIGN KEY (responsible_id) REFERENCES public.tb_user(id);

-- public.tb_finance_detail foreign keys
ALTER TABLE public.tb_finance_detail ADD CONSTRAINT fkb060umt1h9slcku2oha1tcwn0 FOREIGN KEY (created_by) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_finance_detail ADD CONSTRAINT fkcu1h684eftdp310klo78qrkc1 FOREIGN KEY (updated_by) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_finance_detail ADD CONSTRAINT fkovvv4d9d18bxmigwjdcnddh39 FOREIGN KEY (equipment_id) REFERENCES public.tb_equipment(id);
ALTER TABLE public.tb_finance_detail ADD CONSTRAINT fkql7kry24k2ojnck92npf1rhy FOREIGN KEY (finance_id) REFERENCES public.tb_finance(id);

-- public.tb_finance_transaction foreign keys
ALTER TABLE public.tb_finance_transaction ADD CONSTRAINT fk7d8r33x6g6i5aipitbergtyj2 FOREIGN KEY (finance_id) REFERENCES public.tb_finance(id);
ALTER TABLE public.tb_finance_transaction ADD CONSTRAINT fkfbsym6cw1gy3qgiet689u3gkh FOREIGN KEY (user_id) REFERENCES public.tb_user(id);

-- public.tb_solicitation foreign keys
ALTER TABLE public.tb_solicitation ADD CONSTRAINT fk88ofphj28kn58vh151i2nuium FOREIGN KEY (responsible_id) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_solicitation ADD CONSTRAINT fkajrki8u7sfi8nx9qtbh3abu2t FOREIGN KEY (form_id) REFERENCES public.tb_solicitation_form(id);
ALTER TABLE public.tb_solicitation ADD CONSTRAINT fkdw5mecywrq858t9qvcv4u4slb FOREIGN KEY (project_id) REFERENCES public.tb_project(id);
ALTER TABLE public.tb_solicitation ADD CONSTRAINT fkgo2oerqvcl4y85vnfgxgntlme FOREIGN KEY (updated_by) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_solicitation ADD CONSTRAINT fkt2m8qs77gnaj2gwj5c8qycsif FOREIGN KEY (created_by) REFERENCES public.tb_user(id);

-- public.tb_solicitation_form foreign keys
ALTER TABLE public.tb_solicitation_form ADD CONSTRAINT fkdh4rs92rqua7wmf8emmurogd0 FOREIGN KEY (solicitation_id) REFERENCES public.tb_solicitation(id);

-- public.tb_solicitation_form_gradient foreign keys
ALTER TABLE public.tb_solicitation_form_gradient ADD CONSTRAINT fkqt665chy19q0nqg9nn4k0vg03 FOREIGN KEY (form_id) REFERENCES public.tb_solicitation_form(id);

-- public.tb_solicitation_historic foreign keys
ALTER TABLE public.tb_solicitation_historic ADD CONSTRAINT fk52gm4wvo3ye32xunl257t3pyt FOREIGN KEY (solicitation_id) REFERENCES public.tb_solicitation(id);
ALTER TABLE public.tb_solicitation_historic ADD CONSTRAINT fkeh8rihsb9w5qorvwq53qt2yg FOREIGN KEY (created_by) REFERENCES public.tb_user(id);

-- public.tb_solicitation_sample foreign keys
ALTER TABLE public.tb_solicitation_sample ADD CONSTRAINT fkgsrbk1l3exyyqpxwu2qjlrstm FOREIGN KEY (form_id) REFERENCES public.tb_solicitation_form(id);
ALTER TABLE public.tb_solicitation_sample ADD CONSTRAINT fkr31esvc0e4qiwkefp2kdo4ta3 FOREIGN KEY (microscopy_model) REFERENCES public.tb_attachment(id);

-- public.tb_solicitation_sample_analysis foreign keys
ALTER TABLE public.tb_solicitation_sample_analysis ADD CONSTRAINT fke5i3au49qcp05p4ouwxsjpju FOREIGN KEY (equipment_id) REFERENCES public.tb_equipment(id);
ALTER TABLE public.tb_solicitation_sample_analysis ADD CONSTRAINT fkj7vpo9ndnqail95xahgtg1eh FOREIGN KEY (sample_id) REFERENCES public.tb_solicitation_sample(id);

-- public.tb_solicitation_sample_fotos foreign keys
ALTER TABLE public.tb_solicitation_sample_fotos ADD CONSTRAINT fk9g9ghx3jfen47x7atu2yb4mp8 FOREIGN KEY (fotos_id) REFERENCES public.tb_solicitation_sample_photo(id);
ALTER TABLE public.tb_solicitation_sample_fotos ADD CONSTRAINT fkrbhkpu370mqp2e19flnyc2dkw FOREIGN KEY (tb_solicitation_sample_id) REFERENCES public.tb_solicitation_sample(id);

-- public.tb_solicitation_sample_photo foreign keys
ALTER TABLE public.tb_solicitation_sample_photo ADD CONSTRAINT fkrtmmtsxfjj1kge5pwy4d4fdcb FOREIGN KEY (sample_id) REFERENCES public.tb_solicitation_sample(id);

-- public.tb_solicitation_terms_of_use foreign keys
ALTER TABLE public.tb_solicitation_terms_of_use ADD CONSTRAINT fk7yujo6bgt53xl0s6fgarc31xk FOREIGN KEY (terms_of_use_id) REFERENCES public.tb_terms_of_use(id);
ALTER TABLE public.tb_solicitation_terms_of_use ADD CONSTRAINT fk9a75r5kwv437yb9xtmotujuu0 FOREIGN KEY (solicitation_id) REFERENCES public.tb_solicitation(id);