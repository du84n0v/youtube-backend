package com.youtube.controller;

import com.youtube.dto.profile.ProfileChangePasswordDTO;
import com.youtube.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/change/password")
    public ResponseEntity<String> changePassword(@RequestBody ProfileChangePasswordDTO dto){
        return ResponseEntity.ok(profileService.changePassword(dto));
    }

}
