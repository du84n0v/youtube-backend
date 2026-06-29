package com.youtube.mapper.subscription;

import com.youtube.enums.NotificationTypeEnum;

import java.time.LocalDateTime;

public interface SubscriptionInfoMapper {
    Integer getId();
    NotificationTypeEnum getType();
    String getChannelId();
    String getChannelName();
    String getChannelPhotoId();
    LocalDateTime getCreatedDate();
}