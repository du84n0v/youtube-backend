package com.youtube.service;


import com.youtube.dto.tag.response.TagResponseDto;
import com.youtube.dto.tag.response.VideoTagFullInfoDTO;
import com.youtube.dto.videotag.VideoTagDTO;
import com.youtube.entity.ChannelEntity;
import com.youtube.entity.TagEntity;
import com.youtube.entity.VideoEntity;
import com.youtube.entity.VideoTagEntity;
import com.youtube.exception.AppBadException;
import com.youtube.exception.ItemNotFoundException;
import com.youtube.repository.TagRepository;
import com.youtube.repository.VideoRepository;
import com.youtube.repository.VideoTagRepository;
import com.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoTagService {

    @Autowired
    private VideoTagRepository videoTagRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private VideoRepository videoRepository;

    public String addTagToVideo(VideoTagDTO dto) {
        VideoEntity video = videoRepository.findById(dto.getVideoId())
                .orElseThrow(() -> new AppBadException("Video not found!"));

        checkOwner(video, SpringSecurityUtil.getCurrentProfileId());

        TagEntity tag = tagRepository.findById(dto.getTagId())
                .orElseThrow(() -> new AppBadException("Tag not found!"));

        if (videoTagRepository.existsByVideoIdAndTagId(dto.getVideoId(), dto.getTagId())) {
            throw new AppBadException("Video tag already exists!");

        }
        VideoTagEntity videoTag = new VideoTagEntity();
        videoTag.setVideoId(video.getId());
        videoTag.setTagId(tag.getId());
        videoTagRepository.save(videoTag);

        return "Successfully added";
    }

    public String deleteTagFromVideo(VideoTagDTO dto) {
        VideoEntity video = videoRepository.findById(dto.getVideoId())
                .orElseThrow(() -> new AppBadException("Video not found"));

        checkOwner(video, SpringSecurityUtil.getCurrentProfileId());

        Optional<VideoTagEntity> optional = videoTagRepository.findByVideoIdAndTagId(dto.getVideoId(), dto.getTagId());
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Video tag not found");
        }

        videoTagRepository.delete(optional.get());

        return "Successfully deleted";
    }

    public List<VideoTagFullInfoDTO> getTagListByVideoId(String videoId) {
        videoRepository.findById(videoId).orElseThrow(() -> new AppBadException("video not found!"));

        List<VideoTagEntity> videoTags = videoTagRepository.findAllByVideoId(videoId);

        List<VideoTagFullInfoDTO> response = new ArrayList<>();
        for (VideoTagEntity videoTag : videoTags) {
            VideoTagFullInfoDTO dto = new VideoTagFullInfoDTO();
            dto.setId(videoTag.getId());
            dto.setVideoId(videoTag.getVideoId());
            dto.setCreatedDate(videoTag.getCreatedDate());
            dto.setTagInfo(new TagResponseDto(videoTag.getTagId(), videoTag.getTag().getName(), null));

            response.add(dto);
        }

        return response;
    }

    private void checkOwner(VideoEntity videoEntity, Integer currentProfileId) {
        ChannelEntity channel = videoEntity.getChannel();

        if (channel == null) {
            throw new AppBadException("Channel not found!");
        }
        if (!channel.getProfileId().equals(currentProfileId)) {
            throw new AppBadException("You are not owner of this video!");
        }
    }

    public List<TagResponseDto> geTagsByVideoId(String videoId) {
        return videoTagRepository.getVideoTagEntitiesByVideoId(videoId)
                .stream()
                .map(vt -> new TagResponseDto(vt.getTagId(), vt.getTag().getName(), null))
                .toList();
    }
}