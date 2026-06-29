package com.youtube.dto.report;

import com.youtube.entity.ProfileEntity;
import com.youtube.enums.ReportTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseReportDTO {
    private Integer id;
    private Integer profileId;
    private String content;
    private String entityId;
    private ReportTypeEnum videoType;
    private LocalDateTime createdDate;

    public ResponseReportDTO(Integer id, String content, String entityId, Integer profileId, ReportTypeEnum videoType, LocalDateTime createdDate) {
        this.id=id;
        this.content=content;
        this.entityId=entityId;
        this.profileId=profileId;
        this.videoType=videoType;
        this.createdDate=createdDate;

    }
}
