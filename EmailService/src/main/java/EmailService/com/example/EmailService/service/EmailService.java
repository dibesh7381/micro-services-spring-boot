package EmailService.com.example.EmailService.service;

import EmailService.com.example.EmailService.entity.*;
import EmailService.com.example.EmailService.event.*;
import EmailService.com.example.EmailService.repository.EmailLogRepository;
import EmailService.com.example.EmailService.repository.OrderEmailLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    // CI/CD pipeline test - no functional changes

    // 🚀 GHCR Migration Test - EmailService Live K8s Rollout

    private final EmailLogRepository emailLogRepository;
    private final OrderEmailLogRepository orderEmailLogRepository;

    public void sendWelcomeEmail(UserCreatedEvent event) {

        EmailLog log = new EmailLog();
        log.setUsername(event.getUsername());
        log.setEmail(event.getEmail());
        log.setSubject("Welcome to our Application");
        log.setMessage("Hello " + event.getUsername() + ", Welcome to our Application!");
        log.setStatus("SENT");
        log.setSentAt(LocalDateTime.now());

        emailLogRepository.save(log);

        System.out.println("==================================");
        System.out.println("WELCOME EMAIL");
        System.out.println("To      : " + event.getEmail());
        System.out.println("Subject : Welcome to our Application");
        System.out.println();
        System.out.println("Hello " + event.getUsername() + ",");
        System.out.println("Welcome to our Microservices Application!");
        System.out.println("==================================");
    }

    public void sendOrderConfirmation(OrderCreatedEvent event) {

        OrderEmailLog log = new OrderEmailLog();
        log.setOrderId(event.getOrderId());
        log.setUserId(event.getUserId());
        log.setProductName(event.getProductName());
        log.setProductQuantity(event.getProductQuantity());
        log.setTotalPrice(event.getTotalPrice());
        log.setSubject("Order Confirmation");
        log.setMessage("Your order for " + event.getProductName() +
                " (Qty: " + event.getProductQuantity() + ") has been placed successfully!");
        log.setStatus("SENT");
        log.setSentAt(LocalDateTime.now());

        orderEmailLogRepository.save(log);

        System.out.println("==================================");
        System.out.println("ORDER CONFIRMATION EMAIL");
        System.out.println("Order Id : " + event.getOrderId());
        System.out.println("User Id  : " + event.getUserId());
        System.out.println("Product  : " + event.getProductName());
        System.out.println("Quantity : " + event.getProductQuantity());
        System.out.println("Total    : " + event.getTotalPrice());
        System.out.println("==================================");
    }
}