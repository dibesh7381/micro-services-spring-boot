package OrderService.com.example.OrderService.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderCreateRequest {

    private String productName;

    private Integer productQuantity;

    private BigDecimal totalPrice;

}
