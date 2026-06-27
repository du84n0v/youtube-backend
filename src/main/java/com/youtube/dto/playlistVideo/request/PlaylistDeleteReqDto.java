package com.youtube.dto.playlistVideo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistDeleteReqDto {
    private Integer playlistId;
    private String videoId;
}
