package com.youtube.dto.emailhistory;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailHistoryDTO {
    private Integer id;
    private String toEmail;
    private String title;
    private String message;
    private String code;
    private LocalDateTime createdDate;
}