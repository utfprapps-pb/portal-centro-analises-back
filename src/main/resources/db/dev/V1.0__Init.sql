CREATE SEQUENCE public.tb_analysis_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_domain_role_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_email_code_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_email_config_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_email_log_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_equipment_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_partner_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_permission_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_project_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_solicitation_attachments_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_solicitation_historic_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_solicitation_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_student_professor_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_student_solicitation_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_transaction_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE public.tb_user_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

-- public.tb_domain_role definition
CREATE TABLE public.tb_domain_role (
                                       id bigserial NOT NULL,
                                       "domain" varchar(255) NULL,
                                       "role" int2 NULL,
                                       CONSTRAINT tb_domain_role_pkey PRIMARY KEY (id),
                                       CONSTRAINT tb_domain_role_role_check CHECK (((role >= 0) AND (role <= 4))),
                                       CONSTRAINT unique_domain_role UNIQUE (domain)
);

-- public.tb_email_config definition
CREATE TABLE public.tb_email_config (
                                        id bigserial NOT NULL,
                                        email varchar(255) NULL,
                                        host varchar(255) NULL,
                                        "password" varchar(255) NULL,
                                        port int4 NULL,
                                        CONSTRAINT tb_email_config_pkey PRIMARY KEY (id)
);


-- public.tb_email_log definition
CREATE TABLE public.tb_email_log (
                                     id int8 NOT NULL,
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

-- public.tb_equipment definition
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
                                     CONSTRAINT tb_equipment_pkey PRIMARY KEY (id),
                                     CONSTRAINT tb_equipment_status_check CHECK (((status >= 0) AND (status <= 1)))
);

-- public.tb_partner definition
CREATE TABLE public.tb_partner (
                                   id bigserial NOT NULL,
                                   cnpj varchar(20) NOT NULL,
                                   "name" varchar(255) NULL,
                                   status int2 NULL,
                                   CONSTRAINT tb_partner_pkey PRIMARY KEY (id),
                                   CONSTRAINT tb_partner_status_check CHECK (((status >= 0) AND (status <= 1))),
                                   CONSTRAINT uk_cnpj UNIQUE (cnpj)
);

-- public.tb_analysis definition
CREATE TABLE public.tb_analysis (
                                    id bigserial NOT NULL,
                                    description varchar(255) NULL,
                                    "name" varchar(255) NULL,
                                    equipment_id int8 NULL,
                                    CONSTRAINT tb_analysis_pkey PRIMARY KEY (id),
                                    CONSTRAINT fk_tb_analysis_tb_equipment FOREIGN KEY (equipment_id) REFERENCES public.tb_equipment(id)
);

-- public.tb_user definition
CREATE TABLE public.tb_user (
                                id bigserial NOT NULL,
                                balance numeric(38, 2) NULL,
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
                                CONSTRAINT uk_cpf_cnpj UNIQUE (cpf_cnpj),
                                CONSTRAINT fk_tb_user_tb_partner FOREIGN KEY (partner_id) REFERENCES public.tb_partner(id)
);

-- public.tb_email_code definition
CREATE TABLE public.tb_email_code (
                                      id bigserial NOT NULL,
                                      code varchar(255) NULL,
                                      created_at timestamp(6) NULL,
                                      validate_at timestamp(6) NULL,
                                      user_id int8 NULL,
                                      CONSTRAINT tb_email_code_pkey PRIMARY KEY (id),
                                      CONSTRAINT uk_raf31370qu7jml83qgdjwn8t8 UNIQUE (user_id),
                                      CONSTRAINT fk_tb_email_code_tb_user FOREIGN KEY (user_id) REFERENCES public.tb_user(id)
);

-- public.tb_permission definition
CREATE TABLE public.tb_permission (
                                      id bigserial NOT NULL,
                                      "action" int2 NULL,
                                      description varchar(50) NOT NULL,
                                      user_id int8 NULL,
                                      CONSTRAINT tb_permission_action_check CHECK (((action >= 0) AND (action <= 3))),
                                      CONSTRAINT tb_permission_pkey PRIMARY KEY (id),
                                      CONSTRAINT fk_tb_permission_tb_user FOREIGN KEY (user_id) REFERENCES public.tb_user(id)
);


-- public.tb_project definition
CREATE TABLE public.tb_project (
                                   id bigserial NOT NULL,
                                   description varchar(255) NULL,
                                   other_project_nature varchar(255) NULL,
                                   project_nature varchar(255) NULL,
                                   subject varchar(255) NULL,
                                   user_id int8 NULL,
                                   CONSTRAINT tb_project_pkey PRIMARY KEY (id),
                                   CONSTRAINT tb_project_project_nature_check CHECK (((project_nature)::text = ANY ((ARRAY['MASTERS_THESIS'::character varying, 'DOCTORATE_DISSERTATION'::character varying, 'UNDERGRADUATE_THESIS'::character varying, 'INTERNSHIP_PROJECT'::character varying, 'SCIENTIFIC_INITIATION'::character varying, 'EXTENSION_PROJECT'::character varying, 'RESEARCH_PROJECT'::character varying, 'TEACHING_PROJECT'::character varying, 'OTHER'::character varying])::text[]))),
                                   CONSTRAINT fk_tb_project_tb_user FOREIGN KEY (user_id) REFERENCES public.tb_user(id)
);

-- public.tb_project_student definition
CREATE TABLE public.tb_project_student (
                                           project_id int8 NOT NULL,
                                           user_id int8 NOT NULL,
                                           CONSTRAINT fk_tb_project_student_tb_project FOREIGN KEY (project_id) REFERENCES public.tb_project(id),
                                           CONSTRAINT fk_tb_project_student_tb_user FOREIGN KEY (user_id) REFERENCES public.tb_user(id)
);

-- public.tb_solicitation definition
CREATE TABLE public.tb_solicitation (
                                        id bigserial NOT NULL,
                                        amount_hours numeric(38, 2) NULL,
                                        amount_samples int4 NULL,
                                        created_at timestamp(6) NULL,
                                        form jsonb NULL,
                                        observation varchar(255) NULL,
                                        other_project_nature varchar(255) NULL,
                                        paid bool NOT NULL,
                                        price numeric(38, 2) NULL,
                                        project_nature varchar(255) NULL,
                                        schedule_date timestamp(6) NULL,
                                        solicitation_type varchar(255) NULL,
                                        status int2 NULL,
                                        total_price numeric(38, 2) NULL,
                                        updated_at timestamp(6) NULL,
                                        analysis_id int8 NULL,
                                        user_id int8 NULL,
                                        equipment_id int8 NULL,
                                        professor_id int8 NULL,
                                        project_id int8 NULL,
                                        user_updated_id int8 NULL,
                                        CONSTRAINT tb_solicitation_pkey PRIMARY KEY (id),
                                        CONSTRAINT tb_solicitation_project_nature_check CHECK (((project_nature)::text = ANY ((ARRAY['MASTERS_THESIS'::character varying, 'DOCTORATE_DISSERTATION'::character varying, 'UNDERGRADUATE_THESIS'::character varying, 'INTERNSHIP_PROJECT'::character varying, 'SCIENTIFIC_INITIATION'::character varying, 'EXTENSION_PROJECT'::character varying, 'RESEARCH_PROJECT'::character varying, 'TEACHING_PROJECT'::character varying, 'OTHER'::character varying])::text[]))),
                                        CONSTRAINT tb_solicitation_solicitation_type_check CHECK (((solicitation_type)::text = ANY ((ARRAY['AA'::character varying, 'AW'::character varying, 'CLAE'::character varying, 'COR'::character varying, 'DRX'::character varying, 'DSC'::character varying, 'FTMIR'::character varying, 'MEV'::character varying, 'TGADTA'::character varying])::text[]))),
                                        CONSTRAINT tb_solicitation_status_check CHECK (((status >= 0) AND (status <= 7))),
                                        CONSTRAINT fk_tb_solicitation_tb_user FOREIGN KEY (user_id) REFERENCES public.tb_user(id),
                                        CONSTRAINT fk_tb_solicitation_tb_analysis FOREIGN KEY (analysis_id) REFERENCES public.tb_analysis(id),
                                        CONSTRAINT fk_tb_solicitation_tb_project FOREIGN KEY (project_id) REFERENCES public.tb_project(id),
                                        CONSTRAINT fk_tb_solicitation_tb_user_professor FOREIGN KEY (professor_id) REFERENCES public.tb_user(id),
                                        CONSTRAINT fk_tb_solicitation_tb_user_updated FOREIGN KEY (user_updated_id) REFERENCES public.tb_user(id),
                                        CONSTRAINT fk_tb_solicitation_tb_equipment FOREIGN KEY (equipment_id) REFERENCES public.tb_equipment(id)
);

-- public.tb_solicitation_attachments definition
CREATE TABLE public.tb_solicitation_attachments (
                                                    id bigserial NOT NULL,
                                                    attachments varchar(255) NULL,
                                                    content_type varchar(255) NULL,
                                                    file_name varchar(255) NULL,
                                                    url varchar(255) NULL,
                                                    technical_report_id int8 NULL,
                                                    CONSTRAINT tb_solicitation_attachments_attachments_check CHECK (((attachments)::text = ANY ((ARRAY['REPORT'::character varying, 'TICKET'::character varying, 'INVOICE'::character varying, 'ATTACHMENT'::character varying])::text[]))),
                                                    CONSTRAINT tb_solicitation_attachments_pkey PRIMARY KEY (id),
                                                    CONSTRAINT fk_tb_solicitation_attachments_tb_solicitation FOREIGN KEY (technical_report_id) REFERENCES public.tb_solicitation(id)
);

-- public.tb_solicitation_historic definition
CREATE TABLE public.tb_solicitation_historic (
                                                 id bigserial NOT NULL,
                                                 created_at timestamp(6) NULL,
                                                 observation varchar(255) NULL,
                                                 status int2 NULL,
                                                 created_by int8 NULL,
                                                 solicitation_id int8 NULL,
                                                 CONSTRAINT tb_solicitation_historic_pkey PRIMARY KEY (id),
                                                 CONSTRAINT tb_solicitation_historic_status_check CHECK (((status >= 0) AND (status <= 7))),
                                                 CONSTRAINT fk_tb_solicitation_historic_tb_solicitation FOREIGN KEY (solicitation_id) REFERENCES public.tb_solicitation(id),
                                                 CONSTRAINT fk_tb_solicitation_historic_tb_user FOREIGN KEY (created_by) REFERENCES public.tb_user(id)
);


-- public.tb_student_professor definition
CREATE TABLE public.tb_student_professor (
                                             id bigserial NOT NULL,
                                             approved int2 NULL,
                                             created_at date NULL,
                                             professor int8 NOT NULL,
                                             student int8 NOT NULL,
                                             CONSTRAINT tb_student_professor_approved_check CHECK (((approved >= 0) AND (approved <= 2))),
                                             CONSTRAINT tb_student_professor_pkey PRIMARY KEY (id),
                                             CONSTRAINT fk_tb_student_professor_tb_user_student FOREIGN KEY (student) REFERENCES public.tb_user(id),
                                             CONSTRAINT fk_tb_student_professor_tb_user_professor FOREIGN KEY (professor) REFERENCES public.tb_user(id)
);

-- public.tb_student_solicitation definition
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
                                                CONSTRAINT fk_tb_student_solicitation_tb_user_solicited_by FOREIGN KEY (solicited_by) REFERENCES public.tb_user(id),
                                                CONSTRAINT fk_tb_student_solicitation_tb_user_finished_by FOREIGN KEY (finished_by) REFERENCES public.tb_user(id),
                                                CONSTRAINT fk_tb_student_solicitation_tb_user_solicited_to FOREIGN KEY (solicited_to) REFERENCES public.tb_user(id)
);

-- public.tb_transaction definition
CREATE TABLE public.tb_transaction (
                                       id bigserial NOT NULL,
                                       created_at timestamp(6) NULL,
                                       description varchar(255) NULL,
                                       total_value numeric(38, 2) NULL,
                                       created_by int8 NULL,
                                       solicitation_id int8 NULL,
                                       updated_by int8 NULL,
                                       user_id int8 NULL,
                                       CONSTRAINT tb_transaction_pkey PRIMARY KEY (id),
                                       CONSTRAINT fk_tb_transaction_tb_user_updated_by FOREIGN KEY (updated_by) REFERENCES public.tb_user(id),
                                       CONSTRAINT fk_tb_transaction_tb_user FOREIGN KEY (user_id) REFERENCES public.tb_user(id),
                                       CONSTRAINT fk_tb_transaction_tb_user_created_by FOREIGN KEY (created_by) REFERENCES public.tb_user(id),
                                       CONSTRAINT fk_tb_transaction_tb_solicitation FOREIGN KEY (solicitation_id) REFERENCES public.tb_solicitation(id)
);