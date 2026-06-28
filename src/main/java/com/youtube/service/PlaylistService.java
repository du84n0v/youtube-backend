package com.youtube.service;

import com.youtube.config.SpringConfig;
import com.youtube.dto.playlist.request.PlaylistRequestDto;
import com.youtube.dto.playlist.request.PlaylistUpdateRequestDto;
import com.youtube.dto.playlist.request.PlaylistUpdateStatusRequestDto;
import com.youtube.dto.playlist.response.*;
import com.youtube.entity.PlaylistEntity;
import com.youtube.exception.AppBadException;
import com.youtube.repository.PlaylistRepository;
import com.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    public PlaylistResponseDto create(PlaylistRequestDto dto) {
        PlaylistEntity entity = new PlaylistEntity();
        entity.setChannelId(dto.getChannelId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setOrderNumber(dto.getOrderNumber());
        return toDtoFromEntity(entity);
    }

    public PlaylistResponseDto toDtoFromEntity(PlaylistEntity entity) {
        PlaylistResponseDto dto = new PlaylistResponseDto();
        dto.setId(entity.getId());
        dto.setChannelId(entity.getChannelId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public PlaylistResponseDto update(PlaylistUpdateRequestDto dto) {
        Optional<PlaylistEntity> optional = playlistRepository.findById(dto.getId());
        if (optional.isEmpty()){
            throw new AppBadException("Playlist not found");
        }
        PlaylistEntity entity = optional.get();
        entity.setChannelId(dto.getChannelId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setOrderNumber(dto.getOrderNumber());
        playlistRepository.save(entity);
        return toDtoFromEntity(entity);
    }

    public Boolean delete(Integer id) {
        Optional<PlaylistEntity> optional = playlistRepository.findById(id);
        if (optional.isEmpty()){
            throw new AppBadException("Playlist not found");
        }
        PlaylistEntity entity = optional.get();
        for (String currentProfileRole : SpringSecurityUtil.getCurrentProfileRoles()) {
            if (!currentProfileRole.equals("ADMIN")) {
                if (!SpringSecurityUtil.getCurrentProfileId().equals(entity.getChannel().getProfileId())){
                    throw new AppBadException("Not allowed to delete this playlist");
                }
            }
        }
        entity.setVisible(Boolean.FALSE);
        return Boolean.TRUE;
    }

    public PageImpl<PlaylistResponseDto> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdDate").descending());
        Page<PlaylistEntity> result = playlistRepository.findAll(pageable);
        return toPagWithFullInfo(result, pageable);
    }

    public PageImpl<PlaylistResponseDto> toPagWithFullInfo(Page<PlaylistEntity> result, Pageable pageable) {
        List<PlaylistResponseDto> response = new LinkedList<>();

        List<PlaylistEntity> entities = result.getContent();
        entities.forEach(entity -> {
            response.add(toDtoFromEntity(entity));
        });

        return new PageImpl<>(response, pageable, result.getTotalElements());
    }

    public List<PlaylistResponseDto> getListByUserId(Integer id) {
        Optional<List<PlaylistEntity>> optional = playlistRepository.findByUserId(id);
        if (optional.isEmpty()){
            throw new AppBadException("Playlist not found");
        }
        List<PlaylistResponseDto> response = new LinkedList<>();

        List<PlaylistEntity> entities = optional.get();
        entities.forEach(entity -> {
            response.add(toDtoFromEntity(entity));
        });
        return response;
    }

    public PageImpl<PlaylistShortInfoDto> shortInfoDtoPag(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("orderNumber").descending());
        Page<PlaylistEntity> result = playlistRepository.findAllByOwnerId(SpringSecurityUtil.getCurrentProfileId(), pageable);
        return toPagWithUserJoinedShortInfo(result, pageable);
    }

    public PageImpl<PlaylistShortInfoDto> toPagWithUserJoinedShortInfo(Page<PlaylistEntity> result, Pageable pageable){
        List<PlaylistShortInfoDto>  response = new LinkedList<>();
        List<PlaylistEntity> entities = result.getContent();
        entities.forEach(entity -> {
           response.add(toShortInfoDto(entity));
        });
        return new PageImpl<>(response, pageable, result.getTotalElements());
    }

    public PlaylistShortInfoDto toShortInfoDto(PlaylistEntity entity){
        PlaylistShortInfoDto response = new PlaylistShortInfoDto();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setOrderNumber(entity.getOrderNumber());
        response.setStatus(entity.getStatus());

        // CHANNEL SHORT INFO DTO
        PlaylistCustomizedChannelShortInfoDto  channel = new PlaylistCustomizedChannelShortInfoDto();
        channel.setName(entity.getChannel().getName());
        channel.setId(entity.getChannel().getId());

        // PHOTO SHORT INFO DTO FOR CHANNEL
        PlaylistCustomizedPhotoShortInfoDto photo = new PlaylistCustomizedPhotoShortInfoDto();
        photo.setId(entity.getChannel().getPhotoId());
        photo.setPath(entity.getChannel().getPhoto().getPath());

        // SET PROFILE FOR CHANNEL
        channel.setProfile(toProfileShortInfoDto(entity));

        // SET PHOTO FOR CHANNEL
        channel.setPhoto(photo);

        // SET CHANNEL FOR PLAYLIST
        response.setChannel(channel);
        return response;
    }

    public PlaylistCustomizedProfileShortInfoDto toProfileShortInfoDto(PlaylistEntity entity){
        PlaylistCustomizedProfileShortInfoDto profile = new PlaylistCustomizedProfileShortInfoDto();
        profile.setName(entity.getChannel().getOwner().getName());
        profile.setId(entity.getChannel().getOwner().getId());
        profile.setSurname(entity.getChannel().getOwner().getSurname());

        // PHOTO SHORT INFO DTO FOR PROFILE
        PlaylistCustomizedPhotoShortInfoDto photo = new PlaylistCustomizedPhotoShortInfoDto();
        photo.setId(entity.getChannel().getOwner().getPhotoId());
        photo.setPath(entity.getChannel().getOwner().getPhoto().getPath());

        // SET PHOTO FOR PROFILE
        profile.setPhoto(photo);
        return profile;
    }

    public List<PlaylistShortInfoDto> listByChannelKey(String channelKey) {
        Optional<List<PlaylistEntity>> optional = playlistRepository.findAllByChannelKey(channelKey);
        if (optional.isEmpty()){
            throw new AppBadException("Playlist not found");
        }
        List<PlaylistShortInfoDto> response = new LinkedList<>();
        List<PlaylistEntity> entities = optional.get();
        entities.forEach(entity -> {
            response.add(toShortInfoDto(entity));
        });
        return  response;
    }

    public PlaylistShortInfoDto getById(Integer id) {
        Optional<PlaylistEntity> optional = playlistRepository.findById(id);
        if (optional.isEmpty()){
            throw new AppBadException("Playlist not found");
        }
        PlaylistEntity entity = optional.get();
        return toShortInfoDto(entity);
    }

    public PlaylistResponseDto updateStatus(PlaylistUpdateStatusRequestDto dto) {
        Optional<PlaylistEntity> optional = playlistRepository.findById(dto.getId());
        if (optional.isEmpty()){
            throw new AppBadException("Playlist not found");
        }
        PlaylistEntity entity = optional.get();
        entity.setStatus(dto.getStatus());
        playlistRepository.save(entity);
        return toDtoFromEntity(entity);
    }
}
