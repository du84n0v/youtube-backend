package com.youtube.service;

import com.youtube.enums.EmotionEnum;
import com.youtube.repository.VideoLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoLikeService {

    @Autowired
    private VideoLikeRepository videoLikeRepository;

    public Boolean isUserLikedVideo(Integer profileId, String videoId, EmotionEnum emotion) {
        return videoLikeRepository.existsByProfileIdAndVideoIdAndEmotion(profileId, videoId, emotion);
    }
}
