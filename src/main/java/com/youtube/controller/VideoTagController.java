package com.youtube.controller;


import com.youtube.config.security.CustomUserDetails;
import com.youtube.dto.tag.response.VideoTagFullInfoDTO;
import com.youtube.dto.videotag.VideoTagDTO;
import com.youtube.entity.VideoTagEntity;
import com.youtube.service.VideoTagService;
import org.hibernate.internal.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/video-tag")
public class VideoTagController {

    @Autowired
    private VideoTagService videoTagService;

    @PostMapping("/add")
    public ResponseEntity<String> addTag(@RequestBody VideoTagDTO dto){
        return ResponseEntity.ok(videoTagService.addTagToVideo(dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTag(@RequestBody VideoTagDTO dto){
        return ResponseEntity.ok(videoTagService.deleteTagFromVideo(dto));
    }

    @GetMapping("/list/{videoId}")
    public ResponseEntity<List<VideoTagFullInfoDTO>> getListTag(@PathVariable String videoId){
        return ResponseEntity.ok(videoTagService.getTagListByVideoId(videoId));
    }




}
