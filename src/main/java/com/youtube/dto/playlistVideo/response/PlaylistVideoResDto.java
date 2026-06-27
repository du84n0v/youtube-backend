package com.youtube.dto.playlistVideo.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaylistVideoResDto {
    private Integer id;
    private Integer playlistId;
    private String videoId;
    private Integer orderNumber;
    private LocalDateTime createdDate;
}
