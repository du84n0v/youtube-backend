package com.youtube.repository;

import com.youtube.entity.VideoEntity;
import com.youtube.enums.VideoStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<VideoEntity, String> {

    @Transactional
    @Modifying
    @Query("UPDATE VideoEntity v SET v.status = ?2 WHERE v.id = ?1 AND v.channel.profileId = ?3 ")
    int changeVideoStatus(String videoId, VideoStatusEnum status, Integer profileId);

    @Transactional
    @Modifying
    @Query("UPDATE VideoEntity  v " +
            " SET v.viewCount = v.viewCount + 1 " +
            "WHERE v.id = ?1 ")
    int increaseViewCount(String videoId);

    Integer findViewCountById(String id);
}
