package com.youtube.service;

import com.youtube.dto.video.VideoCreateDTO;
import com.youtube.dto.video.VideoDetailUpdateDTO;
import com.youtube.dto.video.VideoStatusUpdateDTO;
import com.youtube.entity.VideoEntity;
import com.youtube.enums.VideoStatusEnum;
import com.youtube.exception.AppBadException;
import com.youtube.exception.ItemNotFoundException;
import com.youtube.repository.VideoRepository;
import com.youtube.util.SpringSecurityUtil;
import jakarta.validation.Valid;
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
        isProfileChannelOwner(dto.getChannelId());

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

    public String updateDetail(String videoId, VideoDetailUpdateDTO dto) {
        VideoEntity video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ItemNotFoundException("Video not found"));

        isProfileChannelOwner(video.getChannelId());

        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setStatus(dto.getStatus());
        video.setCategoryId(dto.getCategoryId());
        if(!video.getPreviewAttachId().equals(dto.getPreviewAttachId())){
            boolean ok = attachService.delete(video.getAttachId());
            if(!ok){
                throw new AppBadException("Something went wrong");
            }
            video.setPreviewAttachId(dto.getPreviewAttachId());
        }

        videoRepository.save(video);

        return "Successfully updated";
    }

    public String updateStatus(VideoStatusUpdateDTO dto) {
        int effRow = videoRepository.changeVideoStatus(dto.getVideoId(), dto.getStatus(), SpringSecurityUtil.getCurrentProfileId());

        if(effRow > 0){
            return "Successfully changed";
        }
        throw new AppBadException("Video not found or this video is not yours");
    }

    private void isProfileChannelOwner(String channelId){
        if(!channelService.isProfileChannelOwner(SpringSecurityUtil.getCurrentProfileId(), channelId)){
            throw new AppBadException("This video is not yours!");
        }
    }

    public Integer increaseViewCount(String videoId) {
        int effRow = videoRepository.increaseViewCount(videoId);

        if(effRow > 0){
            return videoRepository.findViewCountById(videoId);
        }
        throw new AppBadException("Something went wrong");
    }


}
