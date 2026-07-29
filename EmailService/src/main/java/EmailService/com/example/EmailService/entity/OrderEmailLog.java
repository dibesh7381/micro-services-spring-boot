package EmailService.com.example.EmailService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_email_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long userId;

    private String productName;

    private Integer productQuantity;

    private BigDecimal totalPrice;

    private String subject;

    @Column(length = 1000)
    private String message;

    private String status;

    private LocalDateTime sentAt;
}