package com.youtube.service;

import com.youtube.dto.playlist.request.PlaylistRequestDto;
import com.youtube.dto.playlist.request.PlaylistUpdateRequestDto;
import com.youtube.dto.playlist.response.PlaylistResponseDto;
import com.youtube.entity.PlaylistEntity;
import com.youtube.exception.AppBadException;
import com.youtube.repository.PlaylistRepository;
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
        entity.setVisible(Boolean.FALSE);
        return Boolean.TRUE;
    }

    public PageImpl<PlaylistResponseDto> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdDate").descending());
        Page<PlaylistEntity> result = playlistRepository.findAll(pageable);
        return toPagination(result, pageable);
    }

    public PageImpl<PlaylistResponseDto> toPagination(Page<PlaylistEntity> result, Pageable pageable) {
        List<PlaylistResponseDto> response = new LinkedList<>();

        List<PlaylistEntity> entities = result.getContent();
        entities.forEach(entity -> {
            response.add(toDtoFromEntity(entity));
        });

        return new PageImpl<>(response, pageable, result.getTotalElements());
    }
}
