package com.youtube.dto.report;

import com.youtube.enums.ReportTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestReportDTO {
    private String content;
    private String entityId;
    private ReportTypeEnum videoType;
}
