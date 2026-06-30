package com.youtube.service;

import com.youtube.dto.videolike.request.VideoLikeReqDto;
import com.youtube.dto.videolike.response.*;
import com.youtube.entity.VideoLikeEntity;
import com.youtube.enums.EmotionEnum;
import com.youtube.enums.GeneralLikeStatusEnum;
import com.youtube.exception.ItemNotFoundException;
import com.youtube.repository.VideoLikeRepository;
import com.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoLikeService {

    @Autowired
    private VideoLikeRepository videoLikeRepository;

    public Boolean isUserLikedVideo(Integer profileId, String videoId, EmotionEnum emotion) {
        return videoLikeRepository.existsByProfileIdAndVideoIdAndEmotion(profileId, videoId, emotion);
    }

    public VideoLikeResDto create(VideoLikeReqDto dto) {
        Integer profileId = SpringSecurityUtil.getCurrentProfileId();
        Optional<VideoLikeEntity> optional = videoLikeRepository.findByProfileIdAndVideoId(profileId, dto.getVideoId());

        if (optional.isPresent()) {
            VideoLikeEntity entity = optional.get();
            if (entity.getEmotion().equals(dto.getEmotion())) {
                videoLikeRepository.delete(entity);
                return toDto(entity, GeneralLikeStatusEnum.DELETED);
            } else {
                entity.setEmotion(dto.getEmotion());
                videoLikeRepository.save(entity);
                return toDto(entity, GeneralLikeStatusEnum.UPDATED);
            }
        }
        VideoLikeEntity entity = new VideoLikeEntity();
        entity.setProfileId(profileId);
        entity.setVideoId(dto.getVideoId());
        entity.setEmotion(dto.getEmotion());
        entity.setCreatedDate(LocalDateTime.now());

        videoLikeRepository.save(entity);

        return toDto(entity, GeneralLikeStatusEnum.CREATED);
    }

    public VideoLikeResDto toDto(VideoLikeEntity entity, GeneralLikeStatusEnum status) {
        VideoLikeResDto response = new VideoLikeResDto();
        response.setId(entity.getId());
        response.setProfileId(entity.getProfileId());
        response.setVideoId(entity.getVideoId());
        response.setCreatedDate(entity.getCreatedDate());
        response.setEmotion(entity.getEmotion());
        response.setStatus(status);
        return response;
    }

    public Boolean removeLike(VideoLikeReqDto dto) {
        VideoLikeEntity entity = videoLikeRepository.findByProfileIdAndVideoId(SpringSecurityUtil.getCurrentProfileId(), dto.getVideoId())
                .orElseThrow(() -> new ItemNotFoundException("VideoLike Not Found"));
        videoLikeRepository.delete(entity);
        return Boolean.TRUE;
    }

    public List<VideoLikeInfoResDto> userLikedList() {
        List<VideoLikeEntity> entities = videoLikeRepository.findByProfileId(SpringSecurityUtil.getCurrentProfileId())
                .orElseThrow(() -> new ItemNotFoundException("VideoLike Not Found"));

        List<VideoLikeInfoResDto> response = new LinkedList<>();
        entities.forEach(entity -> {
            response.add(toInfoDto(entity));
        });

        return response;
    }

    public VideoLikeInfoResDto toInfoDto(VideoLikeEntity entity) {
        VideoLikeInfoResDto response = new VideoLikeInfoResDto();
        response.setId(entity.getId());

        VideoLikeCustomVideoResDto videoDto = new VideoLikeCustomVideoResDto();
        videoDto.setId(entity.getVideo().getId());
        videoDto.setName(entity.getVideo().getTitle());
        videoDto.setDuration(entity.getVideo().getAttach().getDuration());

        VideoLikeCustomChannelResDto channelDto = new VideoLikeCustomChannelResDto();
        channelDto.setId(entity.getVideo().getChannel().getId());
        channelDto.setName(entity.getVideo().getChannel().getName());
        videoDto.setChannel(channelDto);

        VideoLikeCustomPrAttachResDto attachDto = new VideoLikeCustomPrAttachResDto();
        attachDto.setId(entity.getVideo().getPreviewPhoto().getId());
        attachDto.setUrl(entity.getVideo().getPreviewPhoto().getPath());

        response.setVideo(videoDto);
        response.setPreviewAttach(attachDto);

        return response;
    }

    public List<VideoLikeInfoResDto> userLikedListByPrId(Integer profileId) {
        List<VideoLikeEntity> entities = videoLikeRepository.findByProfileId(profileId)
                .orElseThrow(() -> new ItemNotFoundException("VideoLike Not Found"));

        List<VideoLikeInfoResDto> response = new LinkedList<>();
        entities.forEach(entity -> {
            response.add(toInfoDto(entity));
        });

        return response;
    }
}
