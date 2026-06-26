package com.youtube.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileEmailVerificationDTO {
    private String email;
    private String code;
}
