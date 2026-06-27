package com.youtube.mapper;

import com.youtube.entity.AttachEntity;
import com.youtube.entity.ChannelEntity;

import java.time.LocalDateTime;

public interface VideoShortInfoMapper {
    String getId();
    String getTitle();
    Long getViewCount();
    LocalDateTime getPublishedDate();
    Long getDuration();
    AttachEntity getPreview();
    ChannelEntity getChannel();

}
