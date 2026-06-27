package com.youtube.service;

import com.youtube.dto.video.VideoCreateDTO;
import com.youtube.entity.VideoEntity;
import com.youtube.enums.VideoStatusEnum;
import com.youtube.exception.AppBadException;
import com.youtube.exception.ItemNotFoundException;
import com.youtube.repository.VideoRepository;
import com.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private AttachService attachService;

    public String create(VideoCreateDTO dto) {
        if(!channelService.isProfileChannelOwner(SpringSecurityUtil.getCurrentProfileId(), dto.getChannelId())){
            throw new AppBadException("You can upload video only your channel");
        }

        if(attachService.findById(dto.getPreviewAttachId()) == null){
            throw new ItemNotFoundException("Preview attach not found");
        }

        if(attachService.findById(dto.getAttachId()) == null){
            throw new ItemNotFoundException("Video not found");
        }

        VideoEntity video = new VideoEntity();

        dtoToEntity(dto, video);

        videoRepository.save(video);

        return "Successfully uploaded";

    }

    private void dtoToEntity(VideoCreateDTO dto, VideoEntity video) {
        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setPreviewAttachId(dto.getPreviewAttachId());
        video.setAttachId(dto.getAttachId());
        video.setCategoryId(dto.getCategoryId());
        video.setChannelId(dto.getChannelId());
        video.setType(dto.getType());
        video.setStatus(dto.getStatus());
        if(dto.getStatus().equals(VideoStatusEnum.PRIVATE)) video.setPublishedDate(null);
        video.setViewCount(0L);
        video.setSharedCount(0L);
        video.setLikeCount(0L);
        video.setDislikeCount(0L);
    }
}
