-- 1-QADAM: Comment Like trigger funksiyasini yaratish
--CREATE OR REPLACE FUNCTION process_comment_like_change()
 --   RETURNS TRIGGER AS $$
--BEGIN
    -- 1. YANGI LAYK QO'SHILGANDA (INSERT)
  --  IF (TG_OP = 'INSERT') THEN
--UPDATE comment
--SET like_count = COALESCE(like_count, 0) + 1
--WHERE id = NEW.comment_id;

-- 2. LAYK OLIB TASHLANGANSA / O'CHIRILSA (DELETE)
--ELSIF (TG_OP = 'DELETE') THEN
--UPDATE comment
--SET like_count = GREATEST(0, COALESCE(like_count, 1) - 1)
--WHERE id = OLD.comment_id;
--END IF;

--RETURN NEW;
--END;
--$$ LANGUAGE plpgsql;


-- 2-QADAM: Eski trigger bo'lsa o'chirib, yangisini comment_like jadvaliga bog'lash
--DROP TRIGGER IF EXISTS trg_comment_like_sync ON comment_like;

--CREATE TRIGGER trg_comment_like_sync
  --  AFTER INSERT OR DELETE ON comment_like
    --FOR EACH ROW
--EXECUTE FUNCTION process_comment_like_change();

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

