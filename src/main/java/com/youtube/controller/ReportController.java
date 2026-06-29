package com.youtube.controller;

import com.youtube.dto.report.RequestReportDTO;
import com.youtube.dto.report.ResponseReportDTO;
import com.youtube.dto.report.ResponseReportInfoDTO;
import com.youtube.service.ReportService;
import com.youtube.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<ResponseReportDTO> create(@RequestBody RequestReportDTO requestReportDTO){
        return ResponseEntity.ok(reportService.create(requestReportDTO));
    }

    @GetMapping("pagination/admin")
    public ResponseEntity<Page<ResponseReportInfoDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(reportService.list(PageUtil.page(page), size));
    }

    @DeleteMapping("/remove/id/{id}/admin")
    public ResponseEntity<String> remove(@PathVariable Integer id){
        return ResponseEntity.ok(reportService.remove(id));
    }

    @GetMapping("get/user/list/id/{id}/admin")
    public ResponseEntity<List<ResponseReportInfoDTO>> getUserReports(@PathVariable Integer id){
        return ResponseEntity.ok(reportService.getuserReportList(id));

    }

}
