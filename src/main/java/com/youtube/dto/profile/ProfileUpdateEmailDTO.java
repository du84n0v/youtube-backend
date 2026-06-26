package com.youtube.dto.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateEmailDTO {
    @Email(message = "Email should be formatted!")
    @NotBlank(message = "Email should not be empty")
    private String email;
}
