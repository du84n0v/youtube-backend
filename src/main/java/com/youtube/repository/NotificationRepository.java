package com.youtube.repository;


import com.youtube.entity.NotificationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Integer> {
    Optional<NotificationEntity> findNotificationById(Integer id);
}
