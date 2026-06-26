package com.youtube.dto.channel;


import com.youtube.enums.GeneralStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChannelInfoDTO {
    String id;
    String name;
    String description;
    String profileId;
    String bannerId;
    String photoId;
    LocalDateTime createdDate;
    GeneralStatusEnum status;


    public ChannelInfoDTO(String id, String name, String description, String profileId, String bannerId, String photoId, LocalDateTime createdDate) {
        this.id=id;
        this.name=name;
        this.description=description;
        this.profileId=profileId;
        this.bannerId=bannerId;
        this.photoId=photoId;
        this.createdDate=createdDate;

    }
    public ChannelInfoDTO(String id, String name, String description, String profileId, String bannerId, String photoId, LocalDateTime createdDate,GeneralStatusEnum status) {
        this.id=id;
        this.name=name;
        this.description=description;
        this.profileId=profileId;
        this.bannerId=bannerId;
        this.photoId=photoId;
        this.createdDate=createdDate;
        this.status=status;

    }
    public ChannelInfoDTO(String id, String name, String description, String bannerId,String photoId, LocalDateTime createdDate) {
        this.id=id;
        this.name=name;
        this.description=description;
        this.bannerId=bannerId;
        this.photoId=photoId;
        this.createdDate=createdDate;

    }
}
