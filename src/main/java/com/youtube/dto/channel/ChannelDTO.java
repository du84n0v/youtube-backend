package com.youtube.dto.channel;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChannelDTO {

    private String id;

    @NotBlank(message = "Ism bo‘sh bo‘lmasligi kerak")
    private String name;

    private String photoId;
    private String description;
    private String bannerId;


}