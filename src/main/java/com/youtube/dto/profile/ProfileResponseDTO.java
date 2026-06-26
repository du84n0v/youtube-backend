package com.youtube.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.youtube.enums.ProfileRoleEnum;
import com.youtube.enums.ProfileStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponseDTO {

    private Integer id;

    private String name;

    private String surname;

    private String username;

    private ProfileRoleEnum role;

    private ProfileStatusEnum status;

    private LocalDateTime createdDate;

    private String jwt;
}