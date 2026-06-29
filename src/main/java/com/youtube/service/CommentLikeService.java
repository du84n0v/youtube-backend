package com.youtube.service;

import com.youtube.dto.commentLike.request.CommentLikeReqDto;
import com.youtube.dto.commentLike.response.CommentLikeInfoResDto;
import com.youtube.dto.commentLike.response.CommentLikeResDto;
import com.youtube.entity.CommentLikeEntity;
import com.youtube.enums.GeneralLikeStatusEnum;
import com.youtube.exception.ItemNotFoundException;
import com.youtube.repository.CommentLikeRepository;
import com.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    public CommentLikeResDto create(CommentLikeReqDto dto) {
        Integer profileId = SpringSecurityUtil.getCurrentProfileId();
        Optional<CommentLikeEntity> optional = commentLikeRepository.findByCommentIdAndProfileId(profileId, dto.getCommentId());

        if (optional.isPresent()) {
            CommentLikeEntity entity = optional.get();
            if (entity.getEmotionEnum().equals(dto.getEmotion())) {
                    commentLikeRepository.delete(entity);

                    return toDto(entity, GeneralLikeStatusEnum.DELETED);
            } else {
                entity.setEmotionEnum(dto.getEmotion());
                commentLikeRepository.save(entity);

                return  toDto(entity, GeneralLikeStatusEnum.UPDATED);
            }
        }

        CommentLikeEntity entity = new CommentLikeEntity();
        entity.setCommentId(dto.getCommentId());
        entity.setProfileId(profileId);
        entity.setEmotionEnum(dto.getEmotion());
        entity.setCreatedDate(LocalDateTime.now());

        commentLikeRepository.save(entity);

        return toDto(entity, GeneralLikeStatusEnum.CREATED);
    }

    public CommentLikeResDto toDto(CommentLikeEntity entity, GeneralLikeStatusEnum status) {
        CommentLikeResDto response = new CommentLikeResDto();
        response.setId(entity.getId());
        response.setCommentId(entity.getCommentId());
        response.setProfileId(entity.getProfileId());
        response.setEmotion(entity.getEmotionEnum());
        response.setCreatedDate(entity.getCreatedDate());
        response.setStatus(status);
        return response;
    }

    public Boolean remove(CommentLikeReqDto dto) {
        CommentLikeEntity entity = commentLikeRepository.findByCommentIdAndProfileId(dto.getCommentId(), SpringSecurityUtil.getCurrentProfileId())
                .orElseThrow(() -> new ItemNotFoundException("Comment Not Found"));
        commentLikeRepository.delete(entity);
        return Boolean.TRUE;
    }

    public List<CommentLikeInfoResDto> userLikedList() {
        List<CommentLikeEntity> entities = commentLikeRepository.findByProfileId(SpringSecurityUtil.getCurrentProfileId())
                .orElseThrow(() -> new ItemNotFoundException("Comment Not Found"));

        List<CommentLikeInfoResDto> response = new LinkedList<>();

        entities.forEach(entity -> {
            response.add(toInfoDto(entity));
        });

        return response;
    }

    public CommentLikeInfoResDto toInfoDto(CommentLikeEntity entity) {
        CommentLikeInfoResDto response = new CommentLikeInfoResDto();
        response.setId(entity.getId());
        response.setCommentId(entity.getCommentId());
        response.setProfileId(entity.getProfileId());
        response.setEmotion(entity.getEmotionEnum());
        response.setCreatedDate(entity.getCreatedDate());
        return response;
    }

    public List<CommentLikeInfoResDto> userLikedListByPrId(Integer profileId) {
        List<CommentLikeEntity> entities = commentLikeRepository.findByProfileId(profileId)
                .orElseThrow(() -> new ItemNotFoundException("Comment Not Found"));

        List<CommentLikeInfoResDto> response = new LinkedList<>();

        entities.forEach(entity -> {
            response.add(toInfoDto(entity));
        });

        return response;
    }
}
