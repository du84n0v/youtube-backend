package com.youtube.dto.videotag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoTagRequestDTO {
    private Integer videoId;
    private Integer tagId;
}