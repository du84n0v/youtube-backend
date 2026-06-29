package com.youtube.repository;

import com.youtube.entity.SubscriptionEntity;
import com.youtube.enums.GeneralStatusEnum;
import com.youtube.enums.NotificationTypeEnum;
import com.youtube.mapper.subscription.SubscriptionInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Integer> {

    SubscriptionEntity getByProfileIdAndChannelId(Integer currentProfileId, String channelId);

    @Transactional
    @Modifying
    @Query("UPDATE SubscriptionEntity s SET s.notification = ?3 " +
            " WHERE s.channelId = ?1 AND s.profileId = ?2")
    int changeNotificationType(String channelId, Integer currentProfileId, NotificationTypeEnum type);

    @Query(
            "SELECT s.id AS id, s.notification AS type, " +
                    "c.id AS channelId, c.name AS channnelName, c.photoId AS channelPhotoid, " +
                    " s.subscribeDate AS createdDate " +
                    " FROM SubscriptionEntity s " +
                    " INNER JOIN s.channel c " +
                    " WHERE s.profileId = ?1 AND s.status = ?2 "
    )
    List<SubscriptionInfoMapper> getAllByProfileIdAndStatus(Integer currentProfileId, GeneralStatusEnum generalStatusEnum);
}
