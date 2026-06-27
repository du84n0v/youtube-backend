package com.youtube.dto.playlist.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistCustomizedProfileShortInfoDto {
    private Integer id;
    private String name;
    private String surname;
    private PlaylistCustomizedPhotoShortInfoDto photo;
}
