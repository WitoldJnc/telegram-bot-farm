CREATE TRIGGER ins_up_before
BEFORE INSERT OR UPDATE OF title
ON public.daily_entity
FOR EACH ROW EXECUTE PROCEDURE daily_entity_ins_up_before();