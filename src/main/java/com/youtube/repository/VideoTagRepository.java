package com.youtube.repository;

import com.youtube.entity.VideoTagEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface VideoTagRepository extends CrudRepository<VideoTagEntity, Integer> {

    boolean existsByVideoIdAndTagId(String videoId, Integer tagId);

    List<VideoTagEntity> findAllByVideoId(String videoId);

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM VideoTagEntity v WHERE v.videoId = :videoId AND v.tagId = :tagId")
    boolean existsCheck(String videoId, Integer tagId);

    Optional<VideoTagEntity> findByVideoIdAndTagId(String videoId, Integer tagId);
}