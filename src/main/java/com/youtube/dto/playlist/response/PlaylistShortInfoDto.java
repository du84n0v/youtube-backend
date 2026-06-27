package com.youtube.dto.playlist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youtube.enums.PlaylistStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistShortInfoDto {
    private Integer id;
    private String name;
    private String description;
    private PlaylistStatusEnum status;
    @JsonProperty(namespace = "order_number")
    private Integer orderNumber;
    private PlaylistCustomizedChannelShortInfoDto channel;
}
