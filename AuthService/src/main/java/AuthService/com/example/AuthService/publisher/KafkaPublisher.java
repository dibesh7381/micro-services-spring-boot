package AuthService.com.example.AuthService.publisher;

import AuthService.com.example.AuthService.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPublisher {

    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    public void publishUserCreated(UserCreatedEvent event) {

        kafkaTemplate.send("user-created", event);

        System.out.println("UserCreatedEvent Published Successfully");
    }
}