package com.youtube.repository;

import com.youtube.entity.VideoLikeEntity;
import com.youtube.enums.EmotionEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VideoLikeRepository extends CrudRepository<VideoLikeEntity, Integer> {

    boolean existsByProfileIdAndVideoIdAndEmotion(Integer profileId, String videoId, EmotionEnum emotion);

    Optional<VideoLikeEntity> findByProfileIdAndVideoId(Integer profileId, String videoId);

    Optional<List<VideoLikeEntity>> findByProfileId(Integer profileId);
}
