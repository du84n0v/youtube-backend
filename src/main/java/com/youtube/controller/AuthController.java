package com.youtube.controller;

import com.youtube.dto.auth.RegisterDTO;
import com.youtube.dto.auth.VerificationDTO;
import com.youtube.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@Valid @RequestBody VerificationDTO dto){
        return ResponseEntity.ok(authService.verify(dto));
    }
}
