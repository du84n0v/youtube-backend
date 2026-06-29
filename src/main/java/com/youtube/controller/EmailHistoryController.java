package com.youtube.controller;

import com.youtube.dto.emailhistory.EmailHistoryDTO;
import com.youtube.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email-history")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService historyService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Page<EmailHistoryDTO>> getList(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(name = "size", defaultValue = "20") Integer size){
        return ResponseEntity.ok(historyService.getList(page-1, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-email")
    public ResponseEntity<Page<EmailHistoryDTO>> getProfileHistory(@RequestParam("email") String email,
                                                                   @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                   @RequestParam(name = "size", defaultValue = "20") Integer size){
        return ResponseEntity.ok(historyService.getProfileHistory(email, page-1, size));
    }
}
