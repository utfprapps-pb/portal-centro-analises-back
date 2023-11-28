--EMAIL-CODE
--ALTER TABLE EMAIL_CODE ADD COLUMN CREATED_AT TIMESTAMP; --JÁ EXISTE NO BANCO

--EmailModel
ALTER TABLE TB_EMAIL ADD COLUMN CREATED_AT TIMESTAMP;
ALTER TABLE TB_EMAIL ADD COLUMN CREATED_BY VARCHAR(255);

--EQUIPAMENT
ALTER TABLE EQUIPMENT ADD COLUMN CREATED_AT TIMESTAMP;
ALTER TABLE EQUIPMENT ADD COLUMN CREATED_BY VARCHAR(255);
ALTER TABLE EQUIPMENT ADD COLUMN MODIFIED_AT TIMESTAMP;
ALTER TABLE EQUIPMENT ADD COLUMN MODIFIED_BY VARCHAR(255);

--PARTNER
ALTER TABLE PARTNER ADD COLUMN CREATED_AT TIMESTAMP;
ALTER TABLE PARTNER ADD COLUMN CREATED_BY VARCHAR(255);
ALTER TABLE PARTNER ADD COLUMN MODIFIED_AT TIMESTAMP;
ALTER TABLE PARTNER ADD COLUMN MODIFIED_BY VARCHAR(255);

--PROJECT
ALTER TABLE PROJECT ADD COLUMN CREATED_AT TIMESTAMP;
ALTER TABLE PROJECT ADD COLUMN CREATED_BY VARCHAR(255);
ALTER TABLE PROJECT ADD COLUMN MODIFIED_AT TIMESTAMP;
ALTER TABLE PROJECT ADD COLUMN MODIFIED_BY VARCHAR(255);

--TECHNICAL_REPORT
ALTER TABLE TECHNICAL_REPORT  ADD COLUMN CREATED_AT TIMESTAMP;
ALTER TABLE TECHNICAL_REPORT ADD COLUMN CREATED_BY VARCHAR(255);
ALTER TABLE TECHNICAL_REPORT ADD COLUMN MODIFIED_AT TIMESTAMP;
ALTER TABLE TECHNICAL_REPORT ADD COLUMN MODIFIED_BY VARCHAR(255);

--TRANSACTION
ALTER TABLE TRANSACTION ADD COLUMN CREATED_BY VARCHAR(255);
UPDATE TRANSACTION SET CREATED_BY = TRANSACTION.CREATE_BY;
ALTER TABLE TRANSACTION DROP COLUMN CREATE_BY;

--StudantSolicitation
ALTER TABLE STUDENT_SOLICITATION ADD COLUMN CREATED_AT TIMESTAMP;
UPDATE STUDENT_SOLICITATION SET CREATED_AT = CREATION_DATE;
ALTER TABLE STUDENT_SOLICITATION DROP CREATION_DATE;
ALTER TABLE STUDENT_SOLICITATION ADD COLUMN CREATED_BY VARCHAR(255);
ALTER TABLE STUDENT_SOLICITATION ADD COLUMN MODIFIED_AT TIMESTAMP;
ALTER TABLE STUDENT_SOLICITATION ADD COLUMN MODIFIED_BY VARCHAR(255);

--USERS
ALTER TABLE USERS ADD COLUMN MODIFIED_AT TIMESTAMP;
ALTER TABLE USERS ADD COLUMN MODIFIED_BY VARCHAR(255);
UPDATE USERS SET MODIFIED_AT = UPDATED_AT;
ALTER TABLE USERS DROP COLUMN UPDATED_AT;