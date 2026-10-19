-- Migração para converter colunas timestamp para timestamp com time zone (TIMESTAMPTZ)

ALTER TABLE public.tb_solicitation_sample_analysis
ALTER COLUMN start_date TYPE TIMESTAMPTZ USING start_date AT TIME ZONE 'UTC',
    ALTER COLUMN end_date TYPE TIMESTAMPTZ USING end_date AT TIME ZONE 'UTC';