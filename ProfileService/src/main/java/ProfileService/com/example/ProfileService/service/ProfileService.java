package ProfileService.com.example.ProfileService.service;

import ProfileService.com.example.ProfileService.dto.ProfileDto;
import ProfileService.com.example.ProfileService.entity.Profile;
import ProfileService.com.example.ProfileService.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    // CI/CD pipeline test - no functional changes

    private final ProfileRepository profileRepository;

    public ProfileDto getMyProfile(Long userId) {

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found."));

        ProfileDto response = new ProfileDto();
        response.setId(profile.getId());
        response.setUsername(profile.getUsername());
        response.setEmail(profile.getEmail());
        response.setRole(profile.getRole());

        return response;
    }
}