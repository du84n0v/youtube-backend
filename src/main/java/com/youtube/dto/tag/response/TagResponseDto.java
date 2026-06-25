package com.youtube.dto.tag.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TagResponseDto {
    private Integer id;
    private String name;
    private LocalDateTime createdDate;
}
