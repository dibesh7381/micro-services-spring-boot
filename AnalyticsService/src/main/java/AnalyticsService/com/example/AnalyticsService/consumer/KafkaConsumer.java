package AnalyticsService.com.example.AnalyticsService.consumer;

import AnalyticsService.com.example.AnalyticsService.event.UserCreatedEvent;
import AnalyticsService.com.example.AnalyticsService.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final AnalyticsService analyticsService;

    @KafkaListener( topics = "user-created", groupId = "analytics-group")
    public void consume(UserCreatedEvent event) {

        analyticsService.updateAnalytics(event.getTotalUsers());

        System.out.println("==================================");
        System.out.println("Analytics Updated Successfully");
        System.out.println("Total Users : " + event.getTotalUsers());
        System.out.println("==================================");
    }
}
