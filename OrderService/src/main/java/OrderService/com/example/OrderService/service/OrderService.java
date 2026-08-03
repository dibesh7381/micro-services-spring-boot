package OrderService.com.example.OrderService.service;

import OrderService.com.example.OrderService.dto.OrderCreateRequest;
import OrderService.com.example.OrderService.dto.OrderResponseDto;
import OrderService.com.example.OrderService.dto.OrderUpdateRequest;
import OrderService.com.example.OrderService.entity.Order;
import OrderService.com.example.OrderService.event.OrderCreatedEvent;
import OrderService.com.example.OrderService.publisher.KafkaPublisher;
import OrderService.com.example.OrderService.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    // 🚀 GHCR Migration Test - OrderService Live K8s Rollout

    // CI/CD test: Trigger OrderService deployment using dynamic SHA image tag.

    private final OrderRepository orderRepository;
    private final KafkaPublisher kafkaPublisher;

    public OrderResponseDto createOrder(Long userId, OrderCreateRequest request) {

        Order order = new Order();

        order.setUserId(userId);
        order.setProductName(request.getProductName());
        order.setProductQuantity(request.getProductQuantity());
        order.setTotalPrice(request.getTotalPrice());

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getProductName(),
                savedOrder.getProductQuantity(),
                savedOrder.getTotalPrice()
        );

        kafkaPublisher.publishOrderCreated(event);

        return mapToDto(savedOrder);
    }

    public List<OrderResponseDto> getMyOrders(Long userId) {

        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<OrderResponseDto> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public OrderResponseDto updateOrder(Long orderId, OrderUpdateRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order Not Found"));

        order.setProductName(request.getProductName());
        order.setProductQuantity(request.getProductQuantity());
        order.setTotalPrice(request.getTotalPrice());

        Order updatedOrder = orderRepository.save(order);

        return mapToDto(updatedOrder);
    }

    public void deleteOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order Not Found"));

        orderRepository.delete(order);
    }

    private OrderResponseDto mapToDto(Order order) {

        OrderResponseDto dto = new OrderResponseDto();

        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setProductName(order.getProductName());
        dto.setProductQuantity(order.getProductQuantity());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderDate(order.getOrderDate());

        return dto;
    }
}