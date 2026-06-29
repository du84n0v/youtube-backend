package com.youtube.dto.video;

import com.youtube.dto.AttachShortInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoPlaylistInfoDTO {
    private String id;
    private String title;
    private Long viewCount;
    private LocalDateTime publishedDate;
    private Long duration;
    private AttachShortInfoDTO preview;
}
