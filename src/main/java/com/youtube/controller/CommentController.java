package com.youtube.controller;

import com.youtube.dto.comment.request.CommentReqDto;
import com.youtube.dto.comment.response.CommentInfoResDto;
import com.youtube.dto.comment.response.CommentResDto;
import com.youtube.service.CommentService;
import com.youtube.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pai/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentResDto>  create(@RequestBody CommentReqDto dto) {
        return ResponseEntity.ok(commentService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentResDto> update(@PathVariable Integer id, @RequestBody CommentReqDto dto) {
        return ResponseEntity.ok(commentService.update(id, dto));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommentResDto> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.delete(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<CommentResDto>> pagination(@RequestParam(name = "page")  int page,
                                                              @RequestParam(name = "size") int size) {
        return ResponseEntity.ok(commentService.pagination(PageUtil.page(page), size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/{profileId}/")
    public ResponseEntity<List<CommentResDto>> list(@PathVariable Integer profileId) {
        return ResponseEntity.ok(commentService.listByProfileId(profileId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CommentResDto>> listByCurrentProfile() {
        return ResponseEntity.ok(commentService.listByCurrentProfile());
    }

    @GetMapping("/list/replies/{commentId}")
    public ResponseEntity<List<CommentInfoResDto>> replyListById(@PathVariable Integer commentId) {
        return ResponseEntity.ok(commentService.replyListById(commentId));
    }
}
