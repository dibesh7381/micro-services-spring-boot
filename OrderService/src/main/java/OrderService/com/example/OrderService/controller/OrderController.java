package OrderService.com.example.OrderService.controller;

import OrderService.com.example.OrderService.dto.ApiResponseDto;
import OrderService.com.example.OrderService.dto.OrderCreateRequest;
import OrderService.com.example.OrderService.dto.OrderResponseDto;
import OrderService.com.example.OrderService.dto.OrderUpdateRequest;
import OrderService.com.example.OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> createOrder(
            @RequestBody OrderCreateRequest request,
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        OrderResponseDto response = orderService.createOrder(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto<>(
                        true,
                        "Order Created Successfully",
                        response
                ));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponseDto<List<OrderResponseDto>>> getMyOrders(
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        List<OrderResponseDto> response = orderService.getMyOrders(userId);

        return ResponseEntity.ok(
                new ApiResponseDto<>(
                        true,
                        "Orders Fetched Successfully",
                        response
                )
        );
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponseDto<List<OrderResponseDto>>> getAllOrders() {

        List<OrderResponseDto> response = orderService.getAllOrders();

        return ResponseEntity.ok(
                new ApiResponseDto<>(
                        true,
                        "Orders Fetched Successfully",
                        response
                )
        );
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderUpdateRequest request) {

        OrderResponseDto response = orderService.updateOrder(id, request);

        return ResponseEntity.ok(
                new ApiResponseDto<>(
                        true,
                        "Order Updated Successfully",
                        response
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponseDto<Void>> deleteOrder(
            @PathVariable Long id) {

        orderService.deleteOrder(id);

        return ResponseEntity.ok(
                new ApiResponseDto<>(
                        true,
                        "Order Deleted Successfully",
                        null
                )
        );
    }
}