package com.youtube.dto.tag.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoTagFullInfoDTO {
    private Integer id;
    private String videoId;
    private TagResponseDto tagInfo;
    private LocalDateTime createdDate;
}
