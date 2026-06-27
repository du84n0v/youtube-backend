package com.youtube.dto.playlistVideo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistVideoReqDto {
    private Integer playlistId;
    private String videoId;
    private Integer orderNumber;
}
