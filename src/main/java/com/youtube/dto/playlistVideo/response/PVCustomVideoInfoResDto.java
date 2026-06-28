package com.youtube.dto.playlistVideo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PVCustomVideoInfoResDto {
    private String id;
    @JsonProperty(namespace = "preview_attach")
    private PVCustomPAttachInfoResDto previewAttach;
    private String title;
    private Long duration;
}
