package com.youtube.controller;

import com.youtube.dto.tag.request.TagRequestDto;
import com.youtube.dto.tag.request.TagUpdateRequestDto;
import com.youtube.dto.tag.response.TagResponseDto;
import com.youtube.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<TagResponseDto> create(@RequestBody TagRequestDto dto) {
        return ResponseEntity.ok(tagService.create(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<TagResponseDto> update(@RequestBody TagUpdateRequestDto dto) {
        return ResponseEntity.ok(tagService.update(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TagResponseDto> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(tagService.delete(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TagResponseDto>> getAll(){
        return ResponseEntity.ok(tagService.getAll());
    }
}
