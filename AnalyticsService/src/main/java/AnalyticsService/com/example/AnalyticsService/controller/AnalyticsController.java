package AnalyticsService.com.example.AnalyticsService.controller;

import AnalyticsService.com.example.AnalyticsService.dto.ApiResponseDto;
import AnalyticsService.com.example.AnalyticsService.entity.Analytics;
import AnalyticsService.com.example.AnalyticsService.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AnalyticsController {

    private final AnalyticsRepository analyticsRepository;

    @GetMapping("/total-users")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponseDto<Analytics>> getAnalytics() {

        Analytics analytics = analyticsRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Analytics not found"));

        ApiResponseDto<Analytics> response = new ApiResponseDto<>();
        response.setSuccess(true);
        response.setMessage("Analytics fetched successfully");
        response.setData(analytics);

        return ResponseEntity.ok(response);
    }
}