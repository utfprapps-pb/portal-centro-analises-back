ALTER TABLE public.tb_finance
    ADD COLUMN adjustment_value numeric(38, 2) NOT NULL DEFAULT 0,
    ADD COLUMN adjustment_reason varchar(255) NULL;

ALTER TABLE public.tb_finance
    ADD CONSTRAINT tb_finance_adjustment_reason_check
        CHECK (adjustment_value = 0 OR adjustment_reason IS NOT NULL);