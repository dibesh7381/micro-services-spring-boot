package OrderService.com.example.OrderService.service;

import OrderService.com.example.OrderService.dto.OrderCreateRequest;
import OrderService.com.example.OrderService.dto.OrderResponseDto;
import OrderService.com.example.OrderService.dto.OrderUpdateRequest;
import OrderService.com.example.OrderService.entity.Order;
import OrderService.com.example.OrderService.publisher.KafkaPublisher;
import OrderService.com.example.OrderService.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private KafkaPublisher kafkaPublisher;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {

        order = new Order();
        order.setId(1L);
        order.setUserId(100L);
        order.setProductName("Laptop");
        order.setProductQuantity(2);
        order.setTotalPrice(BigDecimal.valueOf(120000));
        order.setOrderDate(LocalDateTime.now());
    }

    // ================= CREATE ORDER =================

    @Test
    void createOrder_ShouldCreateOrderSuccessfully() {

        OrderCreateRequest request = new OrderCreateRequest();
        request.setProductName("Laptop");
        request.setProductQuantity(2);
        request.setTotalPrice(BigDecimal.valueOf(120000.0));

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponseDto response = orderService.createOrder(100L, request);

        assertNotNull(response);
        assertEquals("Laptop", response.getProductName());

        verify(orderRepository).save(any(Order.class));
        verify(kafkaPublisher).publishOrderCreated(any());
    }

    // ================= GET MY ORDERS =================

    @Test
    void getMyOrders_ShouldReturnOrders() {

        when(orderRepository.findByUserId(100L))
                .thenReturn(List.of(order));

        List<OrderResponseDto> response = orderService.getMyOrders(100L);

        assertEquals(1, response.size());
        assertEquals("Laptop", response.get(0).getProductName());
    }

    @Test
    void getMyOrders_ShouldReturnEmptyList() {

        when(orderRepository.findByUserId(100L))
                .thenReturn(List.of());

        List<OrderResponseDto> response = orderService.getMyOrders(100L);

        assertTrue(response.isEmpty());
    }

    // ================= GET ALL ORDERS =================

    @Test
    void getAllOrders_ShouldReturnOrders() {

        when(orderRepository.findAll())
                .thenReturn(List.of(order));

        List<OrderResponseDto> response = orderService.getAllOrders();

        assertEquals(1, response.size());
    }

    // ================= UPDATE =================

    @Test
    void updateOrder_ShouldUpdateSuccessfully() {

        OrderUpdateRequest request = new OrderUpdateRequest();
        request.setProductName("Mouse");
        request.setProductQuantity(5);
        request.setTotalPrice(BigDecimal.valueOf(3000.0));

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        OrderResponseDto response = orderService.updateOrder(1L, request);

        assertNotNull(response);

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateOrder_ShouldThrowException_WhenOrderNotFound() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> orderService.updateOrder(1L, new OrderUpdateRequest()));
    }

    // ================= DELETE =================

    @Test
    void deleteOrder_ShouldDeleteSuccessfully() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        orderService.deleteOrder(1L);

        verify(orderRepository).delete(order);
    }

    @Test
    void deleteOrder_ShouldThrowException_WhenOrderNotFound() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> orderService.deleteOrder(1L));
    }
}