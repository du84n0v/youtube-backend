package com.youtube.dto.playlist.request;

import com.youtube.enums.PlaylistStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistUpdateStatusRequestDto {
    private Integer id;
    private PlaylistStatusEnum status;
}
