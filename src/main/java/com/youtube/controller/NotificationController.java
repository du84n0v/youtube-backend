package com.youtube.controller;

import com.youtube.dto.notification.NotificationFilterDTO;
import com.youtube.dto.notification.NotificationRequestDTO;
import com.youtube.dto.notification.NotificationResponseDTO;
import com.youtube.service.NotificationService;
import com.youtube.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<NotificationResponseDTO> send(@RequestBody NotificationRequestDTO dto){
        return ResponseEntity.ok(notificationService.send(dto));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<NotificationResponseDTO>> filter(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestBody NotificationFilterDTO dto
    ){
        return ResponseEntity.ok(notificationService.filter(dto, PageUtil.page(page),size));
    }
}
