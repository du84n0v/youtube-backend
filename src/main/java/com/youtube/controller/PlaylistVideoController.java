package com.youtube.controller;

import com.youtube.dto.playlistVideo.request.PlaylistDeleteReqDto;
import com.youtube.dto.playlistVideo.request.PlaylistVideoReqDto;
import com.youtube.dto.playlistVideo.response.PlaylistVideoInfoResDto;
import com.youtube.dto.playlistVideo.response.PlaylistVideoResDto;
import com.youtube.service.PlaylistVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlist-controller")
public class PlaylistVideoController {
    @Autowired
    private PlaylistVideoService playlistVideoService;

    @PostMapping("/create")
    public ResponseEntity<PlaylistVideoResDto> create(@RequestBody PlaylistVideoReqDto dto){
        return ResponseEntity.ok(playlistVideoService.create(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<PlaylistVideoResDto> update(@RequestBody PlaylistVideoReqDto dto){
        return ResponseEntity.ok(playlistVideoService.udpate(dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody PlaylistDeleteReqDto dto){
        return ResponseEntity.ok(playlistVideoService.delete(dto));
    }

    @GetMapping("/get/video-list/{playlist-id}")
    public ResponseEntity<List<PlaylistVideoInfoResDto>>  getVideoList(@PathVariable(name = "playlist-id") Integer playlistId){
        return ResponseEntity.ok(playlistVideoService.getVideoList(playlistId));
    }
}
