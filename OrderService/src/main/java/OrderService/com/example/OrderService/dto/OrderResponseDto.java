package OrderService.com.example.OrderService.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderResponseDto {

    private Long id;

    private Long userId;

    private String productName;

    private Integer productQuantity;

    private BigDecimal totalPrice;

    private LocalDateTime orderDate;

}
