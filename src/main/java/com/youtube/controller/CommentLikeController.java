package com.youtube.controller;

import com.youtube.dto.commentLike.request.CommentLikeReqDto;
import com.youtube.dto.commentLike.response.CommentLikeInfoResDto;
import com.youtube.dto.commentLike.response.CommentLikeResDto;
import com.youtube.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment_like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/create")
    public ResponseEntity<CommentLikeResDto> create(@RequestBody CommentLikeReqDto dto) {
        return ResponseEntity.ok(commentLikeService.create(dto));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Boolean> remove(@RequestBody CommentLikeReqDto dto) {
        return ResponseEntity.ok(commentLikeService.remove(dto));
    }

    @GetMapping("/list/user-liked")
    public ResponseEntity<List<CommentLikeInfoResDto>> userLikedList() {
        return ResponseEntity.ok(commentLikeService.userLikedList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/user-liked/{profileId}")
    public ResponseEntity<List<CommentLikeInfoResDto>> userLikedByPrId(@PathVariable Integer profileId) {
        return ResponseEntity.ok(commentLikeService.userLikedListByPrId(profileId));
    }
}
