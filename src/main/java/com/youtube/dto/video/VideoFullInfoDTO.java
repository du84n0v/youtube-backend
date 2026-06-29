package com.youtube.dto.video;

import com.youtube.dto.AttachShortInfoDTO;
import com.youtube.dto.category.response.CategoryShortDTO;
import com.youtube.dto.channel.ChannelShortInfoDTO;
import com.youtube.dto.tag.response.TagResponseDto;
import com.youtube.dto.videolike.LikeFullDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VideoFullInfoDTO {
    private String id;
    private String title;
    private String description;
    private Long viewCount;
    private Long sharedCount;
    private Long duration;

    private AttachShortInfoDTO preview;

    private AttachShortInfoDTO video;

    private CategoryShortDTO category;

    private List<TagResponseDto> tagList;

    private ChannelShortInfoDTO channel;

    private LikeFullDTO likeInfo;
}
