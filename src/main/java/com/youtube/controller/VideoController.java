package com.youtube.controller;

import com.youtube.dto.video.VideoCreateDTO;
import com.youtube.dto.video.VideoStatusUpdateDTO;
import com.youtube.dto.video.VideoDetailUpdateDTO;
import com.youtube.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody VideoCreateDTO dto){
        return ResponseEntity.ok(videoService.create(dto));
    }

    @PutMapping("/update/detail/{videoId}")
    public ResponseEntity<String> updateDetail(@PathVariable String videoId,
                                               @Valid @RequestBody VideoDetailUpdateDTO dto){
        return ResponseEntity.ok(videoService.updateDetail(videoId, dto));
    }

    @PutMapping("/update/status")
    public ResponseEntity<String> updateStatus(@Valid @RequestBody VideoStatusUpdateDTO dto){
        return ResponseEntity.ok(videoService.updateStatus(dto));
    }

}
