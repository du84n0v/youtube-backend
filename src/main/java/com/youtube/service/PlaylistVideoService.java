package com.youtube.service;

import com.youtube.dto.playlistVideo.request.PlaylistDeleteReqDto;
import com.youtube.dto.playlistVideo.request.PlaylistVideoReqDto;
import com.youtube.dto.playlistVideo.response.*;
import com.youtube.entity.PlaylistVideoEntity;
import com.youtube.exception.AppBadException;
import com.youtube.exception.ItemNotFoundException;
import com.youtube.repository.PlaylistVideoRepository;
import com.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistVideoService {
    @Autowired
    private PlaylistVideoRepository playlistVideoRepository;

    public PlaylistVideoResDto create(PlaylistVideoReqDto dto) {
        PlaylistVideoEntity entity = new PlaylistVideoEntity();
        entity.setPlaylistId(dto.getPlaylistId());
        entity.setVideoId(dto.getVideoId());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setCreatedDate(LocalDateTime.now());
        playlistVideoRepository.save(entity);
        return toDtoFromEntity(entity);
    }

    public PlaylistVideoResDto toDtoFromEntity(PlaylistVideoEntity entity) {
        PlaylistVideoResDto dto = new PlaylistVideoResDto();
        dto.setId(entity.getId());
        dto.setVideoId(entity.getVideoId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setPlaylistId(entity.getPlaylistId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PlaylistVideoResDto update(PlaylistVideoReqDto dto) {
        // FIND ENTITY
        PlaylistVideoEntity entity =  playlistVideoRepository.findById(dto.getPlaylistId())
                .orElseThrow(() -> new ItemNotFoundException("Playlist Video Not Found"));

        if (!SpringSecurityUtil.getCurrentProfileRoles().getFirst().equals("ROLE_ADMIN")) {
            if (!entity.getPlaylist().getChannel().getOwner().getId().equals(SpringSecurityUtil.getCurrentProfileId())){
                throw new AppBadException("You are not the owner of this playlist");
            }
        }
        // SET IF EXISTS
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setVideoId(dto.getVideoId());
        entity.setPlaylistId(dto.getPlaylistId());
        entity.setCreatedDate(LocalDateTime.now());

        // SAVE TO DB
        playlistVideoRepository.save(entity);

        // RETURN DTO
        return toDtoFromEntity(entity);
    }

    public Boolean delete(PlaylistDeleteReqDto dto) {
        Optional<PlaylistVideoEntity> optional = playlistVideoRepository.findByPlaylistIdAndVideoId(dto.getPlaylistId(), dto.getVideoId());
        if (optional.isEmpty()){
            throw new AppBadException("Playlist Video Not Found");
        }

        // DELETE BY SETTING VISIBLE TO FALSE
        PlaylistVideoEntity entity = optional.get();
        entity.setVisible(Boolean.FALSE);

        //SAVE TO DB
        playlistVideoRepository.save(entity);

        return Boolean.TRUE;
    }

    public List<PlaylistVideoInfoResDto> getVideoList(Integer playlistId) {
        List<PlaylistVideoEntity> entities = playlistVideoRepository.findByPlaylistId(playlistId)
                .orElseThrow(() -> new ItemNotFoundException("Playlist Video Not Found"));

        List<PlaylistVideoInfoResDto> response = new ArrayList<>();

        entities.forEach(entity -> {
            response.add(toInfoDtoFromEntity(entity));
        });
        return response;
    }

    public PlaylistVideoInfoResDto toInfoDtoFromEntity(PlaylistVideoEntity entity) {
        PlaylistVideoInfoResDto dto = new PlaylistVideoInfoResDto();
        dto.setPlaylistId(entity.getPlaylistId());
        dto.setDuration(dto.getDuration());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setOrderNumber(entity.getOrderNumber());


        // VIDEO DTO
        PVCustomVideoInfoResDto video = new PVCustomVideoInfoResDto();
        video.setId(entity.getVideoId());
        video.setTitle(entity.getVideo().getTitle());
        video.setDuration(entity.getVideo().getAttach().getDuration());

        // PREVIEW ATTACH DTO
        PVCustomPAttachInfoResDto attach = new PVCustomPAttachInfoResDto();
        attach.setId(entity.getVideo().getAttach().getId());
        attach.setUrl(entity.getVideo().getAttach().getPath());

        // CHANNEL DTO
        PVCustomChannelInfoResDto channel = new PVCustomChannelInfoResDto();
        channel.setId(entity.getVideo().getChannel().getId());
        channel.setName(entity.getVideo().getChannel().getName());

        // SET PREVIEW ATTACH FOR VIDEO DTO
        video.setPreviewAttach(attach);

        // SET VIDEO FOR INFO DTO
        dto.setVideo(video);

        // SET CHANNEL FOR INFO DTO
        dto.setChannel(channel);

        return dto;
    }
}
