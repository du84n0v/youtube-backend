package com.youtube.dto.playlist.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistCustomizedChannelShortInfoDto {
    private String name;
    private String description;
    private PlaylistCustomizedProfileShortInfoDto profile;
}
