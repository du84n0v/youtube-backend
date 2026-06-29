package com.youtube.mapper;

import com.youtube.entity.AttachEntity;
import com.youtube.entity.CategoryEntity;
import com.youtube.entity.ChannelEntity;

public interface VideoFullInfoMapper {
    String getId();
    String getTitle();
    String getDescription();
    Long getViewCount();
    Long getSharedCount();
    Long getLikeCount();
    Long getDislikeCount();

    AttachEntity getPreview();

    AttachEntity getVideo();

    CategoryEntity getCategory();

    ChannelEntity getChannel();
}
