package ProfileService.com.example.ProfileService.service;

import ProfileService.com.example.ProfileService.dto.ProfileDto;
import ProfileService.com.example.ProfileService.entity.Profile;
import ProfileService.com.example.ProfileService.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    private Profile profile;

    @BeforeEach
    void setUp() {

        profile = new Profile();
        profile.setId(1L);
        profile.setUsername("John");
        profile.setEmail("john@example.com");
        profile.setRole("USER");
    }

    @Test
    void getMyProfile_ShouldReturnProfile() {

        when(profileRepository.findById(1L))
                .thenReturn(Optional.of(profile));

        ProfileDto response = profileService.getMyProfile(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John", response.getUsername());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("USER", response.getRole());
    }

    @Test
    void getMyProfile_ShouldThrowException_WhenProfileNotFound() {

        when(profileRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> profileService.getMyProfile(1L)
        );

        assertEquals("Profile not found.", exception.getMessage());
    }
}