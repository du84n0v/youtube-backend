package com.youtube.dto.videotag.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoTagDTO {

    private Integer id;
    private Integer videoId;
    private Integer tagId;
    private LocalDateTime createdDate;
}
