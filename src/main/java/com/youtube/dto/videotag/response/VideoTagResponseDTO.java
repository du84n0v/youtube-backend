package com.youtube.dto.videotag.response;


import com.youtube.dto.tag.request.TagUpdateRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoTagResponseDTO {

    private Integer id;
    private Integer videoId;
    private Integer tagId;
    private LocalDateTime createdDate;
    private TagUpdateRequestDto tag;
}
