package com.youtube.dto.channel;

import com.youtube.enums.GeneralStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChannelResponseDTO {
    String id;
    String name;
    String description;
    Integer profileId;
    String bannerId;
    String photoId;
    LocalDateTime createdDate;
    GeneralStatusEnum status;

    public ChannelResponseDTO(String id, String name, String description, Integer profileId, String bannerId, String photoId, LocalDateTime createdDate, GeneralStatusEnum status) {
        this.id=id;
        this.name=name;
        this.description=description;
        this.profileId=profileId;
        this.bannerId=bannerId;
        this.photoId=photoId;
        this.createdDate=createdDate;
        this.status=status;

    }
}
