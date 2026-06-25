package com.youtube.dto.category.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryResponseDto {
    private Integer id;
    private String name;
    private LocalDateTime createdDate;
}
