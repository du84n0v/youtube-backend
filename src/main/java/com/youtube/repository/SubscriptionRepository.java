package com.youtube.repository;

import com.youtube.entity.SubscriptionEntity;
import com.youtube.enums.NotificationTypeEnum;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Integer> {

    SubscriptionEntity getByProfileIdAndChannelId(Integer currentProfileId, String channelId);

    @Transactional
    @Modifying
    @Query("UPDATE SubscriptionEntity s SET s.notification = ?3 " +
            " WHERE s.channelId = ?1 AND s.profileId = ?2")
    int changeNotificationType(String channelId, Integer currentProfileId, NotificationTypeEnum type);
}
