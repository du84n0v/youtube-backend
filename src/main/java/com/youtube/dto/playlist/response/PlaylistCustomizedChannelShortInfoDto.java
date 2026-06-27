package com.youtube.dto.playlist.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistCustomizedChannelShortInfoDto {
    private String id;
    private String name;
    private PlaylistCustomizedPhotoShortInfoDto photo;
    private PlaylistCustomizedProfileShortInfoDto profile;
}
