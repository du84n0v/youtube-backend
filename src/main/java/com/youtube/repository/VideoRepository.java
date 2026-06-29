package com.youtube.repository;

import com.youtube.entity.VideoEntity;
import com.youtube.enums.VideoStatusEnum;
import com.youtube.mapper.video.VideoAdminShortInfoMapper;
import com.youtube.mapper.video.VideoFullInfoMapper;
import com.youtube.mapper.video.VideoPlaylistInfoMapper;
import com.youtube.mapper.video.VideoShortInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT v.id AS id, v.title AS title, v.viewCount AS viewCount," +
            " v.publishedDate AS publishedDate, v.attach.duration AS duration," +
            " v.previewPhoto as preview, v.channel AS channel " +
            " FROM VideoEntity v " +
            " INNER JOIN v.channel c " +
            " WHERE v.categoryId = ?1 AND v.publishedDate IS NOT NULL ")
    Page<VideoShortInfoMapper> getVideosByCategory(Integer categoryId, Pageable pageable);

    @Query("SELECT v.id AS id, v.title AS title, v.viewCount AS viewCount," +
            " v.publishedDate AS publishedDate, v.attach.duration AS duration," +
            " v.previewPhoto as preview, v.channel AS channel " +
            " FROM VideoEntity v " +
            " INNER JOIN v.channel c " +
            " WHERE v.title ILIKE :search AND v.publishedDate IS NOT NULL ")
    Page<VideoShortInfoMapper> searchByTitle(@Param("search") String search, Pageable pageable);

    @Query("SELECT v.id AS id, v.title AS title, v.viewCount AS viewCount," +
            " v.publishedDate AS publishedDate, v.attach.duration AS duration," +
            " v.previewPhoto as preview, v.channel AS channel " +
            " FROM VideoEntity v " +
            " INNER JOIN VideoTagEntity vt on vt.videoId = v.id " +
            " INNER JOIN v.channel c " +
            " WHERE vt.tagId = ?1 AND v.publishedDate IS NOT NULL ")
    Page<VideoShortInfoMapper> getVideosByTag(Integer tagId, Pageable pageable);

    @Query(
            "SELECT v.id AS id, v.title AS title, v.description as description," +
                    " v.viewCount AS viewCount, v.sharedCount AS sharedCount, " +
                    " v.likeCount AS likeCount, v.dislikeCount AS dislikeCount, " +
                    " v.previewPhoto AS preview, v.attach AS video," +
                    " v.category AS catgory, v.channel AS channel" +
                    " FROM VideoEntity v " +
                    " WHERE (v.id = ?1 AND " +
                    " ((v.status = 'PRIVATE' AND v.channel.profileId = ?2) OR v.status = 'PUBLIC' ))"
    )
    VideoFullInfoMapper getByIdWithFull(String videoId, Integer profileId);

    @Query(
            "SELECT v.id AS id, v.title AS title, v.viewCount AS viewCount," +
                    " v.publishedDate AS publishedDate, v.attach.duration AS duration," +
                    " v.previewPhoto as preview, v.channel AS channel, " +
                    " c.profileId AS profileId, c.owner.name AS profileName, " +
                    " v.channel.owner.surname AS profileSurname, " +
                    " pv.playlistId AS playlistId, pv.playlist.name AS playlistName" +
                    " FROM VideoEntity v " +
                    " INNER JOIN v.channel c " +
                    " LEFT JOIN v.previewPhoto p " +
                    " LEFT JOIN PlaylistVideoEntity pv on pv.videoId = v.id" +
                    " WHERE v.publishedDate IS NOT NULL "
    )
    Page<VideoAdminShortInfoMapper> getList(Pageable pageable);

    @Query(
            "SELECT v.id AS id, v.title AS title, v.viewCount AS viewCount, " +
                    " v.publishedDate AS publishedDate, v.attach.duration AS duration," +
                    " v.previewPhoto AS preview " +
                    " FROM VideoEntity  v " +
                    " LEFT JOIN v.previewPhoto p " +
                    " WHERE v.channelId = ?1 AND v.publishedDate IS NOT NULL " +
                    " ORDER BY v.createdDate DESC "
    )
    Page<VideoPlaylistInfoMapper> getChannelVideos(String channelId, Pageable pageable);
}
