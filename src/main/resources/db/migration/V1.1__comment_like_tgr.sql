DROP TRIGGER IF EXISTS comment_like_tgr ON comment_like;

CREATE OR REPLACE FUNCTION public.comment_like_updater_function()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        IF NEW.emotion = 'LIKE' THEN
UPDATE comment SET like_count = like_count + 1 WHERE id = NEW.comment_id;
ELSIF NEW.emotion = 'DISLIKE' THEN
UPDATE comment SET dislike_count = dislike_count + 1 WHERE id = NEW.comment_id;
END IF;
RETURN NEW;
ELSIF TG_OP = 'DELETE' THEN
        IF OLD.emotion = 'LIKE' THEN
UPDATE comment SET like_count = GREATEST(like_count - 1, 0) WHERE id = OLD.comment_id;
ELSIF OLD.emotion = 'DISLIKE' THEN
UPDATE comment SET dislike_count = GREATEST(dislike_count - 1, 0) WHERE id = OLD.comment_id;
END IF;
RETURN OLD;
ELSIF TG_OP = 'UPDATE' THEN
        IF OLD.emotion = 'LIKE' AND NEW.emotion = 'DISLIKE' THEN
UPDATE comment SET like_count = GREATEST(like_count - 1, 0), dislike_count = dislike_count + 1 WHERE id = NEW.comment_id;
ELSIF OLD.emotion = 'DISLIKE' AND NEW.emotion = 'LIKE' THEN
UPDATE comment SET dislike_count = GREATEST(dislike_count - 1, 0), like_count = like_count + 1 WHERE id = NEW.comment_id;
END IF;
RETURN NEW;
END IF;
RETURN NULL;
END;
$$;

CREATE TRIGGER comment_like_tgr
    AFTER INSERT OR DELETE OR UPDATE ON comment_like
    FOR EACH ROW
EXECUTE FUNCTION comment_like_updater_function();