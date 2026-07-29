package EmailService.com.example.EmailService.consumer;

import EmailService.com.example.EmailService.event.*;
import EmailService.com.example.EmailService.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "user-created", groupId = "email-group")
    public void consumeUserCreated(UserCreatedEvent event) {
        emailService.sendWelcomeEmail(event);
        System.out.println("Welcome Email Processed Successfully");
    }

    @KafkaListener(topics = "order-created", groupId = "email-group")
    public void consumeOrderCreated(OrderCreatedEvent event) {
        emailService.sendOrderConfirmation(event);
        System.out.println("Order Confirmation Email Processed Successfully");
    }
}