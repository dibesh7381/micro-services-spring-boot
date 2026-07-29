package OrderService.com.example.OrderService.publisher;

import OrderService.com.example.OrderService.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPublisher {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publishOrderCreated(OrderCreatedEvent event) {

        kafkaTemplate.send("order-created", event);

        System.out.println("==================================");
        System.out.println("OrderCreatedEvent Published");
        System.out.println("Order Id : " + event.getOrderId());
        System.out.println("User Id  : " + event.getUserId());
        System.out.println("==================================");
    }
}
