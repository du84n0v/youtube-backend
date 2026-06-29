package com.youtube.repository;

import com.youtube.entity.SubscriptionEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Integer> {

    SubscriptionEntity getByProfileIdAndChannelId(Integer currentProfileId, String channelId);
}
