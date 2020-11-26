CREATE OR REPLACE FUNCTION daily_entity_ins_up_before()
  RETURNS trigger AS
$func$
BEGIN

IF EXISTS (SELECT 1 FROM public.daily_entity
           WHERE (title)
           = (NEW.title)) THEN
   RETURN NULL;
END IF;

RETURN NEW;

END
$func$  LANGUAGE plpgsql;

CREATE TRIGGER ins_up_before
BEFORE INSERT OR UPDATE OF title  -- fire only when relevant
ON public.daily_entity
FOR EACH ROW EXECUTE PROCEDURE daily_entity_ins_up_before();