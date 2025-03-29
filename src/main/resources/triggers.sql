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

DROP TRIGGER IF EXISTS trg_update_solicitation_status ON tb_solicitation_historic;

CREATE TRIGGER trg_update_solicitation_status
    BEFORE INSERT OR UPDATE OR DELETE ON tb_solicitation_historic
    FOR EACH ROW
EXECUTE PROCEDURE fn_update_solicitation_status();
