package com.youtube.dto.videolike.response;

import com.youtube.enums.EmotionEnum;
import com.youtube.enums.GeneralLikeStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoLikeResDto {
    private Integer id;
    private Integer profileId;
    private String videoId;
    private EmotionEnum emotion;
    private GeneralLikeStatusEnum status;
    private LocalDateTime createdDate;
}
