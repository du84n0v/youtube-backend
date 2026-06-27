package com.youtube.mapper;

import java.time.LocalDateTime;

public interface VideoShortInfoMapper {
    String getId();
    String getTitle();
    String getPreviewId();
    Long getViewCount();
    LocalDateTime getPublishedDate();
    String getChannelId();
    String getChannelName();
    String getChannelPhotoId();
    Long getDuration();
}
