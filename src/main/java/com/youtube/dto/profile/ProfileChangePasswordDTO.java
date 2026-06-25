package com.youtube.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileChangePasswordDTO {
    private String oldPassword;
    @NotBlank(message = "Password should not be empty!")
    @Size(min = 5, max = 30, message = "Password size should be min 5 and max 30 length!")
    private String newPassword;
}
