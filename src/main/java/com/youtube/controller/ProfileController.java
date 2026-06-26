package com.youtube.controller;

import com.youtube.dto.profile.ProfileEmailVerificationDTO;
import com.youtube.dto.profile.ProfileUpdateEmailDTO;
import com.youtube.dto.profile.ProfileUpdatePasswordDTO;
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
    public ResponseEntity<String> updatePassword(@Valid @RequestBody ProfileUpdatePasswordDTO dto) {
        return ResponseEntity.ok(profileService.updatePassword(dto));
    }

    @PostMapping("/update/email")
    public ResponseEntity<String> updateEmail(@Valid @RequestBody ProfileUpdateEmailDTO dto){
        return ResponseEntity.ok(profileService.updateEmail(dto));
    }
    @PutMapping("/update/email-verify")
    public ResponseEntity<String> verifyAndUpdateEmail(@RequestBody ProfileEmailVerificationDTO dto){
        return ResponseEntity.ok(profileService.verifyAndUpdateEmail(dto));
    }

}
