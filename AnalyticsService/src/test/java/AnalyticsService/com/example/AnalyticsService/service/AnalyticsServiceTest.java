package AnalyticsService.com.example.AnalyticsService.service;

import AnalyticsService.com.example.AnalyticsService.entity.Analytics;
import AnalyticsService.com.example.AnalyticsService.repository.AnalyticsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private AnalyticsRepository analyticsRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    void updateAnalytics_ShouldCreateNewAnalytics_WhenNotExists() {

        when(analyticsRepository.findById(1L))
                .thenReturn(Optional.empty());

        analyticsService.updateAnalytics(10L);

        verify(analyticsRepository).save(any(Analytics.class));
    }

    @Test
    void updateAnalytics_ShouldUpdateExistingAnalytics() {

        Analytics analytics = new Analytics();
        analytics.setId(1L);
        analytics.setTotalUsers(5L);

        when(analyticsRepository.findById(1L))
                .thenReturn(Optional.of(analytics));

        analyticsService.updateAnalytics(20L);

        verify(analyticsRepository).save(analytics);
    }
}