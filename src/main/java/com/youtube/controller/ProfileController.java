package com.youtube.controller;

import com.youtube.dto.profile.*;
import com.youtube.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PutMapping("/update/password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody ProfilePasswordUpdateDTO dto) {
        return ResponseEntity.ok(profileService.updatePassword(dto));
    }

    @PostMapping("/update/email")
    public ResponseEntity<String> updateEmail(@Valid @RequestBody ProfileEmailUpdateDTO dto){
        return ResponseEntity.ok(profileService.updateEmail(dto));
    }

    @PutMapping("/update/email-verify")
    public ResponseEntity<String> verifyAndUpdateEmail(@RequestBody ProfileEmailVerificationDTO dto){
        return ResponseEntity.ok(profileService.verifyAndUpdateEmail(dto));
    }

    @PutMapping("/update/detail")
    public ResponseEntity<String> updateDetail(@Valid @RequestBody ProfileDetailUpdateDTO dto){
        return ResponseEntity.ok(profileService.updateDetail(dto));
    }

    @PutMapping("/update/photo")
    public ResponseEntity<String> updatePhoto(@Valid @RequestBody ProfilePhotoUpdateDTO dto){
        return ResponseEntity.ok(profileService.updatePhoto(dto));
    }

}
