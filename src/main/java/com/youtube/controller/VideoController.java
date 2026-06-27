package com.youtube.controller;

import com.youtube.dto.video.VideoCreateDTO;
import com.youtube.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody VideoCreateDTO dto){
        return ResponseEntity.ok(videoService.create(dto));
    }
}
