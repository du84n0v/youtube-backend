package com.youtube.dto.video;

import com.youtube.enums.VideoStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoStatusUpdateDTO {
    @NotBlank
    private String videoId;
    @NotNull
    private VideoStatusEnum status;
}
