DROP TRIGGER IF EXISTS video_like_tgr ON video_like;

CREATE OR REPLACE FUNCTION public.video_like_updater_function()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        IF NEW.emotion = 'LIKE' THEN
UPDATE video SET like_count = like_count + 1 WHERE id = NEW.video_id;
ELSIF NEW.emotion = 'DISLIKE' THEN
UPDATE video SET dislike_count = dislike_count + 1 WHERE id = NEW.video_id;
END IF;
RETURN NEW;

ELSIF TG_OP = 'DELETE' THEN
        IF OLD.emotion = 'LIKE' THEN
UPDATE video SET like_count = GREATEST(like_count - 1, 0) WHERE id = OLD.video_id;
ELSIF OLD.emotion = 'DISLIKE' THEN
UPDATE video SET dislike_count = GREATEST(dislike_count - 1, 0) WHERE id = OLD.video_id;
END IF;
RETURN OLD;

ELSIF TG_OP = 'UPDATE' THEN
        IF OLD.emotion = 'LIKE' AND NEW.emotion = 'DISLIKE' THEN
UPDATE video SET like_count = GREATEST(like_count - 1, 0), dislike_count = dislike_count + 1 WHERE id = NEW.video_id;
ELSIF OLD.emotion = 'DISLIKE' AND NEW.emotion = 'LIKE' THEN
UPDATE video SET dislike_count = GREATEST(dislike_count - 1, 0), like_count = like_count + 1 WHERE id = NEW.video_id;
END IF;
RETURN NEW;
END IF;

RETURN NULL;
END;
$$;

CREATE TRIGGER video_like_tgr
    AFTER INSERT OR DELETE OR UPDATE ON video_like
    FOR EACH ROW
EXECUTE FUNCTION video_like_updater_function();
