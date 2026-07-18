package com.monal.driveEase.Controller;

import com.monal.driveEase.DTOs.Response.OwnerDashboardResponse;
import com.monal.driveEase.Services.OwnerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnerDashboardController {
    private final OwnerDashboardService ownerDashboardService;
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerDashboardResponse> getDashboard() {

        return ResponseEntity.ok(ownerDashboardService.getDashboard());
    }
}
