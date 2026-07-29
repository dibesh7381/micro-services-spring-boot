package EmailService.com.example.EmailService.service;

import EmailService.com.example.EmailService.entity.EmailLog;
import EmailService.com.example.EmailService.entity.OrderEmailLog;
import EmailService.com.example.EmailService.event.OrderCreatedEvent;
import EmailService.com.example.EmailService.event.UserCreatedEvent;
import EmailService.com.example.EmailService.repository.EmailLogRepository;
import EmailService.com.example.EmailService.repository.OrderEmailLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private EmailLogRepository emailLogRepository;

    @Mock
    private OrderEmailLogRepository orderEmailLogRepository;

    @InjectMocks
    private EmailService emailService;

    @Test
    void sendWelcomeEmail_ShouldSaveEmailLog() {

        UserCreatedEvent event = new UserCreatedEvent();
        event.setUsername("John");
        event.setEmail("john@example.com");

        emailService.sendWelcomeEmail(event);

        ArgumentCaptor<EmailLog> captor = ArgumentCaptor.forClass(EmailLog.class);

        verify(emailLogRepository).save(captor.capture());

        EmailLog log = captor.getValue();

        assertEquals("John", log.getUsername());
        assertEquals("john@example.com", log.getEmail());
        assertEquals("Welcome to our Application", log.getSubject());
        assertEquals("SENT", log.getStatus());
        assertNotNull(log.getSentAt());
        assertTrue(log.getMessage().contains("John"));
    }

    @Test
    void sendOrderConfirmation_ShouldSaveOrderEmailLog() {

        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setOrderId(100L);
        event.setUserId(1L);
        event.setProductName("Laptop");
        event.setProductQuantity(2);
        event.setTotalPrice(BigDecimal.valueOf(120000));

        emailService.sendOrderConfirmation(event);

        ArgumentCaptor<OrderEmailLog> captor =
                ArgumentCaptor.forClass(OrderEmailLog.class);

        verify(orderEmailLogRepository).save(captor.capture());

        OrderEmailLog log = captor.getValue();

        assertEquals(100L, log.getOrderId());
        assertEquals(1L, log.getUserId());
        assertEquals("Laptop", log.getProductName());
        assertEquals(2, log.getProductQuantity());
        assertEquals(BigDecimal.valueOf(120000), log.getTotalPrice());
        assertEquals("Order Confirmation", log.getSubject());
        assertEquals("SENT", log.getStatus());
        assertNotNull(log.getSentAt());
        assertTrue(log.getMessage().contains("Laptop"));
    }
}