package com.youtube.repository;

import com.youtube.entity.VideoTagEntity;
import org.hibernate.internal.util.Optional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoTagRepository extends CrudRepository<VideoTagEntity, Integer> {
    boolean existsByVideoIdAndTagId(Integer videoId, Integer tagId);

    List<VideoTagEntity> findAllByVideoId(Integer videoId);

    Optional<VideoTagEntity>  findByVideoIdAndTagId(Integer videoId, Integer tagId);

    void deleteByVideoIdAndTagId(Integer videoId, Integer tagId);
}
