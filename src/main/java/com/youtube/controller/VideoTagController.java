package com.youtube.controller;


import com.youtube.config.security.CustomUserDetails;
import com.youtube.dto.videotag.request.VideoTagRequestDTO;
import com.youtube.entity.VideoTagEntity;
import com.youtube.mapper.VideoTagInfoMapper;
import com.youtube.service.VideoTagService;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/video-tag")
@RequiredArgsConstructor
public class VideoTagController {

    private final VideoTagService videoTagService;

    @PreAuthorize(("hasRoleAny('USER')"))
    @PostMapping("/add")
    public ResponseEntity<Optional<VideoTagInfoMapper>> addTag(
            @RequestBody VideoTagRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ){
        return ResponseEntity.ok(videoTagService.addTagToVideo(dto, userDetails.getId()));
    }

    @PreAuthorize("hasRoleAny('USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTag(
            @RequestBody VideoTagRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        videoTagService.deleteTagFromVideo(dto, userDetails.getId());
        return ResponseEntity.ok("Tag deleted successfully");
    }

    @GetMapping("list/{videoId}")
    public ResponseEntity<List<VideoTagEntity>> getListTag(
         @PathVariable String videoId
    ){
        return ResponseEntity.ok(videoTagService.getTagListByVideoId(videoId));
    }




}
