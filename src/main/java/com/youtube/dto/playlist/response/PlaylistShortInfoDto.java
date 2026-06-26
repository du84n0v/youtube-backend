package com.youtube.dto.playlist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistShortInfoDto {
    private String name;
    private String description;
    @JsonProperty(namespace = "order_number")
    private Integer orderNumber;
    private PlaylistCustomizedChannelShortInfoDto channel;
}
