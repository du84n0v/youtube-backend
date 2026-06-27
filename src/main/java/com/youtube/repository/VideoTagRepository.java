package com.youtube.repository;

import com.youtube.entity.VideoTagEntity;
import org.hibernate.internal.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VideoTagRepository extends CrudRepository<VideoTagEntity, Integer> {
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM VideoTagEntity v WHERE v.videoId = :videoId AND v.tagId = :tagId")
    boolean existsCheck(String videoId, Integer tagId);

    List<VideoTagEntity> findAllByVideoId(Integer videoId);


    <T> Optional<T> findByVideoIdAndTagId(String videoId, Integer tagId, Class<T> type);


    @Query("DELETE FROM VideoTagEntity v WHERE v.videoId = :videoId AND v.tagId = :tagId")
    @Modifying
    @Transactional
    void deleteByVideoIdAndTagId(String videoId, Integer tagId);



}
