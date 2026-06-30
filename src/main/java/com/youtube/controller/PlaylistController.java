package com.youtube.controller;

import com.youtube.dto.playlist.request.PlaylistRequestDto;
import com.youtube.dto.playlist.request.PlaylistUpdateRequestDto;
import com.youtube.dto.playlist.request.PlaylistUpdateStatusRequestDto;
import com.youtube.dto.playlist.response.PlaylistResponseDto;
import com.youtube.dto.playlist.response.PlaylistShortInfoDto;
import com.youtube.service.PlaylistService;
import com.youtube.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistResponseDto> create(@RequestBody PlaylistRequestDto dto) {
        return ResponseEntity.ok(playlistService.create(dto));
    }

    @PutMapping
    public ResponseEntity<PlaylistResponseDto> update(@RequestBody PlaylistUpdateRequestDto dto) {
        return ResponseEntity.ok(playlistService.update(dto));
    }

    @PutMapping("/status")
    public ResponseEntity<PlaylistResponseDto> updateStatus(@RequestBody PlaylistUpdateStatusRequestDto dto) {
        return ResponseEntity.ok(playlistService.updateStatus(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(playlistService.delete(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<PlaylistResponseDto>> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(playlistService.pagination(PageUtil.page(page), size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<PlaylistResponseDto>> listByUserId(@PathVariable Integer id){
        return ResponseEntity.ok(playlistService.getListByUserId(id));
    }

    @GetMapping("/pagination/short-info")
    public ResponseEntity<PageImpl<PlaylistShortInfoDto>> playlistShortInfoPag(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                               @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(playlistService.shortInfoDtoPag(PageUtil.page(page), size));
    }

    @GetMapping("/channel/{channelKey}")
    public ResponseEntity<List<PlaylistShortInfoDto>>  listByChannel(@PathVariable String channelKey){
        return ResponseEntity.ok(playlistService.listByChannelKey(channelKey));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistShortInfoDto> getById(@PathVariable Integer id){
        return ResponseEntity.ok(playlistService.getById(id));
    }
}