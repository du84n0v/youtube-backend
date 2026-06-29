package com.youtube.dto.report;

import com.youtube.dto.profile.ProfileInfoDTO;
import com.youtube.enums.ReportTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseReportInfoDTO {

    private Integer reportId;

    private ProfileInfoDTO profile;

    private String content;

    private String entityId;

    private ReportTypeEnum type;

    private LocalDateTime createdDate;

    public ResponseReportInfoDTO(Integer id, String content, String entityId, ProfileInfoDTO profileInfoDTO, ReportTypeEnum type, LocalDateTime createdDate) {
        this.reportId = id;
        this.profile = profileInfoDTO;
        this.content = content;
        this.entityId = entityId;
        this.type = type;
        this.createdDate = createdDate;

    }

}