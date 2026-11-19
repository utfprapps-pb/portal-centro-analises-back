-- Alterar scan_range para string
ALTER TABLE public.tb_solicitation_sample
ALTER COLUMN scan_range TYPE varchar(255) USING scan_range::text;