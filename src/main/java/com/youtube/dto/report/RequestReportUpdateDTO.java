package com.youtube.dto.report;

import com.youtube.enums.ReportTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RequestReportUpdateDTO {
    private Integer id;
    private String content;
}
