package com.youtube.dto.playlistVideo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaylistVideoInfoResDto {
    @JsonProperty(namespace = "playlist_id")
    private Integer playlistId;
    private PVCustomVideoInfoResDto video;
    private Long duration;
    private PVCustomChannelInfoResDto channel;
    @JsonProperty(namespace = "created_date")
    private LocalDateTime createdDate;
    @JsonProperty(namespace = "order_number")
    private Integer orderNumber;
}
