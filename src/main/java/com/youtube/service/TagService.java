package com.youtube.service;

import com.youtube.dto.tag.request.TagRequestDto;
import com.youtube.dto.tag.request.TagUpdateRequestDto;
import com.youtube.dto.tag.response.TagResponseDto;
import com.youtube.entity.TagEntity;
import com.youtube.exception.AppBadException;

import com.youtube.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagResponseDto create(TagRequestDto dto) {
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        entity.setCreatedDate(LocalDateTime.now());
        return toDtoFromEntity(entity);
    }

    public TagResponseDto toDtoFromEntity(TagEntity entity) {
        TagResponseDto dto = new TagResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public TagResponseDto update(TagUpdateRequestDto dto) {
        Optional<TagEntity> optional = tagRepository.findById(dto.getId());
        if (optional.isEmpty()){
            throw new AppBadException("Tag not found");
        }
        TagEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setCreatedDate(LocalDateTime.now());
        tagRepository.save(entity);
        return toDtoFromEntity(entity);
    }

    public TagResponseDto delete(Integer id) {
        Optional<TagEntity> optional = tagRepository.findById(id);
        if (optional.isEmpty()){
            throw new AppBadException("Tag not found");
        }
        tagRepository.deleteById(id);
        return toDtoFromEntity(optional.get());
    }

    public List<TagResponseDto> getAll() {
        Iterable<TagEntity> iterable = tagRepository.getAllByOrderByCreatedDateDesc();
        List<TagResponseDto> dtoList = new LinkedList<>();
        iterable.forEach(entity -> dtoList.add(toDtoFromEntity(entity)));
        return dtoList;
    }
}
