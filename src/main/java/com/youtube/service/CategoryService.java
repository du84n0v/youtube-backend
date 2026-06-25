package com.youtube.service;

import com.youtube.dto.category.request.CategoryRequestDto;
import com.youtube.dto.category.request.CategoryUpdateRequestDto;
import com.youtube.dto.category.response.CategoryResponseDto;
import com.youtube.entity.CategoryEntity;
import com.youtube.exception.AppBadException;
import com.youtube.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponseDto create(CategoryRequestDto dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity.setCreatedDate(LocalDateTime.now());
        categoryRepository.save(entity);
        return toDtoFromEntity(entity);
    }

    public CategoryResponseDto toDtoFromEntity(CategoryEntity entity) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryResponseDto update(CategoryUpdateRequestDto dto) {
        Optional<CategoryEntity> optional = categoryRepository.findById(dto.getId());
        if (optional.isEmpty()) {
            throw new AppBadException("Category not found");
        }
        CategoryEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setCreatedDate(LocalDateTime.now());
        categoryRepository.save(entity);
        return toDtoFromEntity(entity);
    }


    public CategoryResponseDto delete(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Category not found");
        }
        categoryRepository.deleteById(id);
        return toDtoFromEntity(optional.get());
    }

    public List<CategoryResponseDto> getAll() {
        Iterable<CategoryEntity> iterable = categoryRepository.getAllByOrderByCreatedDateDesc();
        List<CategoryResponseDto> dtoList = new LinkedList<>();
        iterable.forEach(entity -> dtoList.add(toDtoFromEntity(entity)));
        return dtoList;
    }
}
