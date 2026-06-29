package com.youtube.dto.commentLike.request;

import com.youtube.enums.EmotionEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentLikeReqDto {
    private Integer commentId;
    private EmotionEnum  emotion;
}
