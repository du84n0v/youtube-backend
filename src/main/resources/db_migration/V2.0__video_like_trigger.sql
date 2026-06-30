CREATE OR FUNCTION video_like_updater()
       RETURNS TRIGGER AS $$
BEGIN
 IF (TG_OP = 'INSERT') THEN
    UPDATE video SET like_count = like_count + 1
    WHERE id = NEW.video_id;
 ELSEIF (TG_OP = 'DELETE') THEN
    UPDATE video SET like_count = like_count - 1
    WHERE id = OLD.video_id;
 END IF;

 RETURN NEW;
 END;
 $$ LANGUAGE plpgsql

CREATE TRIGGER video_like_trigger
  AFTER INSERT OR DELETE ON video_like
  FOR EACH ROW
  EXECUTE FUNCTION video_like_updater();

