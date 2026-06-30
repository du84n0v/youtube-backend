package com.youtube.dto.videolike.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoLikeCustomVideoResDto {
    private String id;
    private String name;
    private VideoLikeCustomChannelResDto channel;
    private Long duration;
}
