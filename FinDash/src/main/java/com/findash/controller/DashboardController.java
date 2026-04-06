package com.findash.controller;

import com.findash.dto.ApiResponse;
import com.findash.services.DashboardService;
import com.findash.util.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public ResponseEntity<ApiResponse<?>> dashboard() {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Fetched", service.getSummary())
        );
    }

    @GetMapping("/recent")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public ResponseEntity<ApiResponse<?>> getRecent() {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Recent records", service.getRecent())
        );
    }
}
