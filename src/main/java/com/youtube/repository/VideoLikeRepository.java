package com.youtube.repository;

import com.youtube.entity.VideoLikeEntity;
import com.youtube.enums.EmotionEnum;
import org.springframework.data.repository.CrudRepository;

public interface VideoLikeRepository extends CrudRepository<VideoLikeEntity, Integer> {

    boolean existsByProfileIdAndVideoIdAndEmotion(Integer profileId, String videoId, EmotionEnum emotion);
}
