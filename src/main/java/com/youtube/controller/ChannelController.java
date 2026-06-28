package com.youtube.controller;


import com.youtube.dto.channel.*;
import com.youtube.service.ChannelService;
import com.youtube.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channel")
public class ChannelController {

    @Autowired
    ChannelService channelService;


    @PostMapping("/create")
    public ResponseEntity<ChannelResponseDTO> create(@RequestBody ChannelRequestDTO dto) {
        return ResponseEntity.ok(channelService.create(dto));
    }



    @PutMapping("/update")
    public ResponseEntity<ChannelUpdateDTO> update(@RequestBody ChannelUpdateDTO dto) {
        return ResponseEntity.ok(channelService.update(dto));
    }
    @GetMapping("get/list/admin")
    public ResponseEntity<Page<ChannelInfoDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(channelService.pagination(PageUtil.page(page), size));
    }

    @GetMapping("/get/channel/info/by/id/{id}")
    public ResponseEntity<ChannelInfoDTO> getAll(
            @PathVariable String id
    ) {

        return ResponseEntity.ok(channelService.getChannelInfoById(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change/channel/info/by/id/{id}/admin")
    public ResponseEntity<ChannelInfoDTO> changeStatus(
            @RequestBody ChangeChannelStatusDTO dto
            ) {
        return ResponseEntity.ok(channelService.changeChannelStatusByAdmin(dto));
    }

    @GetMapping("/get/user/channels/by/id")
    public ResponseEntity<List<ChannelInfoDTO>> getUserChannelsById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(channelService.GetUsersChannelsById(id));
    }

}
