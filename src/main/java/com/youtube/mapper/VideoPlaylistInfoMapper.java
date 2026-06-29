package com.youtube.mapper;

import com.youtube.entity.AttachEntity;

import java.time.LocalDateTime;

public interface VideoPlaylistInfoMapper {
    String getId();

    String getTitle();

    Long getViewCount();

    LocalDateTime getPublishedDate();

    Long getDuration();

    AttachEntity getPreview();
}
