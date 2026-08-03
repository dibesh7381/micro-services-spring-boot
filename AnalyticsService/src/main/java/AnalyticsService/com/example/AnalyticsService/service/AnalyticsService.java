package AnalyticsService.com.example.AnalyticsService.service;

import AnalyticsService.com.example.AnalyticsService.entity.Analytics;
import AnalyticsService.com.example.AnalyticsService.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    // CI/CD test: Trigger GitHub Actions to verify dynamic SHA image deployment.

    private final AnalyticsRepository analyticsRepository;

    public void updateAnalytics(Long totalUsers) {

        Analytics analytics = analyticsRepository.findById(1L)
                .orElse(new Analytics());

        analytics.setId(1L);
        analytics.setTotalUsers(totalUsers);

        analyticsRepository.save(analytics);
    }
}