package com.youtube.dto.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDetailUpdateDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
}
