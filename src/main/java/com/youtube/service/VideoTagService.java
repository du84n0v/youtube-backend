package com.youtube.service;


import com.youtube.dto.videotag.request.VideoTagRequestDTO;
import com.youtube.dto.videotag.response.VideoTagResponseDTO;
import com.youtube.entity.ChannelEntity;
import com.youtube.entity.TagEntity;
import com.youtube.entity.VideoEntity;
import com.youtube.entity.VideoTagEntity;
import com.youtube.exception.AppBadException;
import com.youtube.mapper.VideoTagInfoMapper;
import com.youtube.repository.TagRepository;
import com.youtube.repository.VideoRepository;
import com.youtube.repository.VideoTagRepository;
import org.hibernate.internal.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoTagService {

    @Autowired
    private VideoTagRepository videoTagRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private VideoRepository videoRepository;



    public Optional<VideoTagInfoMapper> addTagToVideo(VideoTagRequestDTO dto, Integer currentProfileId ) {

        VideoEntity video = videoRepository.findById(String.valueOf(dto.getVideoId()))
                .orElseThrow(()-> new AppBadException("Video not found!"));

        chekOwner(video,  currentProfileId);

        TagEntity tag = tagRepository.findById(dto.getTagId())
                .orElseThrow(()-> new AppBadException("Tag not found!"));

        if (videoTagRepository.existsByVideoIdAndTagId(dto.getVideoId(),dto.getTagId())) {
            throw new AppBadException("Video tag already exists!");

        }
        VideoTagEntity videoTag = new VideoTagEntity();
        videoTag.setVideoId(video.getId());
        videoTag.setTagId(tag.getId());
        videoTagRepository.save(videoTag);

        return videoTagRepository.findByVideoIdAndTagId(dto.getVideoId(), dto.getTagId());




    }
    public void deleteTagFromVideo(VideoTagRequestDTO dto, Integer currentProfileId) {
        VideoEntity video = videoRepository.findById(String.valueOf(dto.getVideoId()))
                .orElseThrow(() -> new AppBadException("Video not found"));

        chekOwner(video, currentProfileId);

        if (!videoTagRepository.existsByVideoIdAndTagId(dto.getVideoId(), dto.getTagId())) {
            throw new AppBadException("Tag not found in this video");
        }

        videoTagRepository.deleteByVideoIdAndTagId(String.valueOf(dto.getVideoId()), dto.getTagId());
    }

   public List<VideoTagEntity> getTagListByVideoId(String videoId) {
        videoRepository.findById(videoId).orElseThrow(()-> new AppBadException("video not found!"));

        return videoTagRepository.findAllByVideoId(videoId);

   }



    private void chekOwner(VideoEntity videoEntity , Integer currentProfileId) {
        ChannelEntity channel = videoEntity.getChannel();

        if (channel==null) {
            throw new AppBadException("Channel not found!");
        }
        if(!channel.getProfileId().equals(currentProfileId)) {
            throw new AppBadException("You are not owner of this video!");
        }
    }


}
