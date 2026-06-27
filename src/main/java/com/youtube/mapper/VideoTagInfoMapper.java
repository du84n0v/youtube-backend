package com.youtube.mapper;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;

public interface VideoTagInfoMapper {
    Integer getId();
    String getVideoId();
    LocalDateTime getCreatedDate();
    TagInfo getTag();



    interface TagInfo {
        Integer getId();
        String getName();
    }
}
