package com.youtube.controller;

import com.youtube.dto.subscription.SubscriptionDTO;
import com.youtube.dto.subscription.SubscriptionInfoDTO;
import com.youtube.enums.NotificationTypeEnum;
import com.youtube.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody SubscriptionDTO dto) {
        dto.setType(NotificationTypeEnum.ALL);
        return ResponseEntity.ok(subscriptionService.create(dto));
    }

    @PutMapping("/change/notification")
    public ResponseEntity<String> changeNotification(@Valid @RequestBody SubscriptionDTO dto) {
        return ResponseEntity.ok(subscriptionService.changeNotification(dto));
    }

    @GetMapping("/get-list")
    public ResponseEntity<List<SubscriptionInfoDTO>> getSubscriptionList(){
        return ResponseEntity.ok(subscriptionService.getProfileSubscriptionList());
    }

}
