CREATE OR FUNCTION comment_like_updater()
       RETURNS TRIGGER AS $$
BEGIN
 IF (TG_OP = 'INSERT') THEN
UPDATE comment SET like_count = like_count + 1
WHERE id = NEW.comment_id;
ELSEIF (TG_OP = 'DELETE') THEN
UPDATE comment SET like_count = like_count - 1
WHERE id = OLD.comment_id;
END IF;

RETURN NEW;
END;
 $$ LANGUAGE plpgsql

CREATE TRIGGER comment_like_trigger
    AFTER INSERT OR DELETE ON comment_like
  FOR EACH ROW
  EXECUTE FUNCTION comment_like_updater();