package com.youtube.dto.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfilePhotoUpdateDTO {
    @NotBlank(message = "Attach ID bo'sh bo'lishi mumkin emas")
    private String attachId;
}
