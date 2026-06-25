package com.youtube.controller;

import com.youtube.dto.category.request.CategoryRequestDto;
import com.youtube.dto.category.request.CategoryUpdateRequestDto;
import com.youtube.dto.category.response.CategoryResponseDto;
import com.youtube.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDto>  create(@RequestBody CategoryRequestDto dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryResponseDto> update(@RequestBody CategoryUpdateRequestDto dto) {
        return ResponseEntity.ok(categoryService.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CategoryResponseDto> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

}
