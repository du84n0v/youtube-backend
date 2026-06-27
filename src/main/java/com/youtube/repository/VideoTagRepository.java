package com.youtube.repository;

import com.youtube.entity.VideoTagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VideoTagRepository extends CrudRepository<VideoTagEntity, Integer> {

    boolean existsByVideoIdAndTagId(String videoId, Integer tagId);

    List<VideoTagEntity> findAllByVideoId(String videoId);

    Optional<VideoTagEntity> findByVideoIdAndTagId(String videoId, Integer tagId);
}