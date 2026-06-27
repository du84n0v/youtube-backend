package com.youtube.dto.video;

import com.youtube.dto.AttachShortInfoDTO;
import com.youtube.dto.channel.ChannelShortInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoShortInfoDTO {
    private String id;
    private String title;
    private AttachShortInfoDTO preview;
    private LocalDateTime publishedDate;
    private ChannelShortInfoDTO channel;
    private Long viewCount;
    private Long duration;
}
