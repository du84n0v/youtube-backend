package com.youtube.dto.playlist.request;

import com.youtube.enums.PlaylistStatusEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistUpdateRequestDto {
    private Integer id;
    @NotBlank(message = "Channel id is required")
    private String channelId;
    @NotBlank(message = "Name id is required")
    private String name;
    @NotBlank(message = "Description id is required")
    private String description;
    private PlaylistStatusEnum status;
    private Integer orderNumber;
}
