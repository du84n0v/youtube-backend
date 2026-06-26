package com.youtube.dto.profile;

import com.youtube.dto.AttachShortInfoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDetailDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private AttachShortInfoDTO mainPhoto;
}
