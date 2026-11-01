package com.monal.driveEase.Controller;

import com.monal.driveEase.DTOs.Request.NaturalSearchRequest;
import com.monal.driveEase.DTOs.Response.NaturalSearchResponse;
import com.monal.driveEase.DTOs.Response.ReviewSummaryResponse;
import com.monal.driveEase.Services.AiSearchService;
import com.monal.driveEase.Services.ReviewSummaryService;
import com.monal.driveEase.ai.RateLimiter;
import com.monal.driveEase.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AiController {

    private final AiSearchService aiSearchService;
    private final ReviewSummaryService reviewSummaryService;
    private final RateLimiter rateLimiter;

    @PostMapping("/search")
    public ResponseEntity<NaturalSearchResponse> search(
            @RequestBody NaturalSearchRequest request,
            HttpServletRequest http) {

        if (!rateLimiter.allow(http.getRemoteAddr())) {
            throw new BadRequestException("Too many searches. Please wait a minute and try again.");
        }
        return ResponseEntity.ok(aiSearchService.search(request.getQuery()));
    }

    @GetMapping("/reviews/{vehicleId}/summary")
    public ResponseEntity<ReviewSummaryResponse> reviewSummary(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(reviewSummaryService.summarize(vehicleId));
    }
}
