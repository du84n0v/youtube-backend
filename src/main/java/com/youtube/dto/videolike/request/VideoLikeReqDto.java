package com.youtube.dto.videolike.request;

import com.youtube.enums.EmotionEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoLikeReqDto {
    private String videoId;
    private EmotionEnum emotion;
}
