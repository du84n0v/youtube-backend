package com.youtube.repository;

import com.youtube.entity.VideoEntity;
import com.youtube.enums.VideoStatusEnum;
import com.youtube.mapper.VideoShortInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

    @Query("SELECT v.id AS id, v.title AS title, v.previewAttachId AS prewiewId," +
            " v.viewCount AS viewCount, v.publishedDate AS publishedDate, v.channelId AS channelId," +
            " c.name AS channelName, c.photoId AS channelPhotoId, v.attach.duration AS duration " +
            " FROM VideoEntity v " +
            " INNER JOIN ChannelEntity c on c.id = v.channelId " +
            " WHERE v.categoryId = ?1 AND v.publishedDate IS NOT NULL ")
    Page<VideoShortInfoMapper> getVideosByCategory(Integer categoryId, Pageable pageable);
}
