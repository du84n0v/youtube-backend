package com.youtube.controller;

import com.youtube.dto.videolike.request.VideoLikeReqDto;
import com.youtube.dto.videolike.response.VideoLikeInfoResDto;
import com.youtube.dto.videolike.response.VideoLikeResDto;
import com.youtube.service.VideoLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/video-like")
public class VideoLikeController {
    @Autowired
    private VideoLikeService videoLikeService;

    @PostMapping
    public ResponseEntity<VideoLikeResDto> create(@RequestBody VideoLikeReqDto dto) {
        return ResponseEntity.ok(videoLikeService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> remove(@RequestBody VideoLikeReqDto dto) {
        return ResponseEntity.ok(videoLikeService.removeLike(dto));
    }

    @GetMapping("/my")
    public ResponseEntity<List<VideoLikeInfoResDto>> userLikedList(){
        return ResponseEntity.ok(videoLikeService.userLikedList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<VideoLikeInfoResDto>> userLikedList(@PathVariable Integer profileId){
        return ResponseEntity.ok(videoLikeService.userLikedListByPrId(profileId));
    }
}