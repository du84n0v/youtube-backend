package com.youtube.controller;

import com.youtube.dto.subscription.SubscriptionCreateDTO;
import com.youtube.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody SubscriptionCreateDTO dto){
        return ResponseEntity.ok(subscriptionService.create(dto));
    }

}
