package com.youtube.dto.playlist.response;

import com.youtube.enums.PlaylistStatusEnum;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaylistResponseDto {
    private Integer id;
    private String channelId;
    private String name;
    private String description;
    private PlaylistStatusEnum status;
    private Integer orderNumber;
    private LocalDateTime createdDate;
}
