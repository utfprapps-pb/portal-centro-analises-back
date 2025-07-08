CREATE OR REPLACE FUNCTION fn_update_solicitation_status()
    RETURNS TRIGGER AS $$
DECLARE
    v_max_id INTEGER;
BEGIN
    IF TG_OP = 'INSERT' THEN
        -- Em INSERT: atualiza diretamente o status em tb_solicitation com o NEW.status
        UPDATE tb_solicitation
        SET status = NEW.status
        WHERE id = NEW.solicitation_id;
        RETURN NEW;

    ELSIF TG_OP = 'UPDATE' THEN
        -- Em UPDATE: verifica se o NEW.id é o maior para aquela solicitação
        SELECT MAX(id)
        INTO v_max_id
        FROM tb_solicitation_historic
        WHERE solicitation_id = NEW.solicitation_id;

        IF NEW.id >= v_max_id THEN
            UPDATE tb_solicitation
            SET status = NEW.status
            WHERE id = NEW.solicitation_id;
        END IF;
        RETURN NEW;

    ELSIF TG_OP = 'DELETE' THEN
        -- Em DELETE: verifica se existem outros registros para a mesma solicitação,
        -- desconsiderando o registro que está sendo removido (OLD.id)
        SELECT MAX(id)
        INTO v_max_id
        FROM tb_solicitation_historic
        WHERE solicitation_id = OLD.solicitation_id AND id <> OLD.id;

        IF v_max_id IS NULL THEN
            -- Se não houver mais histórico, define status como 0
            UPDATE tb_solicitation
            SET status = 0
            WHERE id = OLD.solicitation_id;
        ELSE
            -- Caso contrário, atualiza com o status do registro com o maior id restante
            UPDATE tb_solicitation
            SET status = (
                SELECT status
                FROM tb_solicitation_historic
                WHERE id = v_max_id
            )
            WHERE id = OLD.solicitation_id;
        END IF;
        RETURN OLD;
    END IF;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tg_update_solicitation_status ON tb_solicitation_historic;

CREATE TRIGGER tg_update_solicitation_status
    BEFORE INSERT OR UPDATE OR DELETE ON tb_solicitation_historic
    FOR EACH ROW
EXECUTE PROCEDURE fn_update_solicitation_status();

------------------------------

CREATE OR REPLACE FUNCTION fn_equipment_analysis_before_delete()
    RETURNS trigger AS $$
BEGIN
    UPDATE tb_equipment
    SET analysis_id = NULL
    WHERE analysis_id = OLD.id;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tg_equipment_analysis_before_delete ON tb_solicitation_amostra_analise;

CREATE TRIGGER tg_equipment_analysis_before_delete
    BEFORE DELETE ON tb_solicitation_sample_analysis
    FOR EACH ROW
EXECUTE PROCEDURE fn_equipment_analysis_before_delete();

----------------

CREATE OR REPLACE FUNCTION fn_equipment_analysis_after_insert_update()
    RETURNS trigger AS $$
BEGIN
    IF (NEW.start_date IS NOT NULL AND NEW.end_date IS NULL) THEN
        UPDATE tb_equipment
        SET analysis_id = NEW.id
        WHERE id = NEW.equipment_id;
    END IF;

    IF (NEW.start_date IS NOT NULL AND NEW.end_date IS NOT NULL) THEN
        UPDATE tb_equipment
        SET analysis_id = NULL
        WHERE id = NEW.equipment_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tg_equipment_analysis_after_insert_update ON tb_solicitation_sample_analysis;

CREATE TRIGGER tg_equipment_analysis_after_insert_update
    AFTER INSERT OR UPDATE ON tb_solicitation_sample_analysis
    FOR EACH ROW
EXECUTE PROCEDURE fn_equipment_analysis_after_insert_update();

------------------------------

CREATE OR REPLACE FUNCTION fn_equipment_analysis_control()
    RETURNS trigger AS $$
BEGIN
    IF (TG_OP = 'UPDATE') THEN
        IF (OLD.analysis_id IS NOT NULL AND NEW.analysis_id IS NOT NULL) THEN
            IF (OLD.analysis_id <> NEW.analysis_id) THEN
                raise exception 'GenericException{Equipamento já esta em uso por outra análise.}';
            END IF;
        END IF;

        RETURN NEW;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tg_equipment_analysis_control ON tb_equipment;

CREATE TRIGGER tg_equipment_analysis_control
    AFTER UPDATE ON tb_equipment
    FOR EACH ROW
EXECUTE PROCEDURE fn_equipment_analysis_control();
