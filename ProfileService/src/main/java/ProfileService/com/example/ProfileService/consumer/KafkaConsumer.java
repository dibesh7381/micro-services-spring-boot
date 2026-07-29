package ProfileService.com.example.ProfileService.consumer;

import ProfileService.com.example.ProfileService.entity.Profile;
import ProfileService.com.example.ProfileService.event.UserCreatedEvent;
import ProfileService.com.example.ProfileService.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProfileRepository profileRepository;

    @KafkaListener( topics = "user-created", groupId = "profile-group")
    public void consume(UserCreatedEvent event) {

        if (profileRepository.existsById(event.getId())) {
            return;
        }

        Profile profile = new Profile();
        profile.setId(event.getId());
        profile.setUsername(event.getUsername());
        profile.setEmail(event.getEmail());
        profile.setRole(event.getRole());

        profileRepository.save(profile);

        System.out.println("Profile Created Successfully");
    }
}