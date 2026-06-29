package com.youtube.dto.profile;

import com.youtube.entity.AttachEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileInfoDTO {

    private Integer id;
    private String name;
    private String surname;
    private String photo_id;
    private String photoStringUrl;
}