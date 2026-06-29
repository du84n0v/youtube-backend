package com.youtube.dto.videolike.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoLikeInfoResDto {
    private Integer id;
    private VideoLikeCustomVideoResDto video;
    @JsonProperty(namespace = "preview_attach")
    private VideoLikeCustomPrAttachResDto previewAttach;
}
