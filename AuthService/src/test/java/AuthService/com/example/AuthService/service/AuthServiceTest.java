package AuthService.com.example.AuthService.service;

import AuthService.com.example.AuthService.dto.*;
import AuthService.com.example.AuthService.entity.User;
import AuthService.com.example.AuthService.event.UserCreatedEvent;
import AuthService.com.example.AuthService.publisher.KafkaPublisher;
import AuthService.com.example.AuthService.repository.UserRepository;
import AuthService.com.example.AuthService.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private KafkaPublisher kafkaPublisher;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1L);
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPassword("encodedPassword");
        user.setRole("USER");
        user.setCreatedAt(LocalDate.now());
    }

    // ================= SIGNUP =================

    @Test
    void signup_ShouldCreateUserSuccessfully() {

        SignupRequestDto request = new SignupRequestDto();
        request.setUsername("John");
        request.setEmail("john@example.com");
        request.setPassword("123456");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.count()).thenReturn(1L);

        SignupResponseDto response = authService.signup(request);

        assertNotNull(response);
        assertEquals("John", response.getUsername());
        assertEquals("john@example.com", response.getEmail());

        verify(userRepository).save(any(User.class));
        verify(kafkaPublisher).publishUserCreated(any(UserCreatedEvent.class));
    }

    @Test
    void signup_ShouldThrowException_WhenEmailAlreadyExists() {

        SignupRequestDto request = new SignupRequestDto();
        request.setEmail("john@example.com");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.signup(request));

        assertEquals("Email already exists.", exception.getMessage());

        verify(userRepository, never()).save(any());
        verify(kafkaPublisher, never()).publishUserCreated(any());
    }

    // ================= LOGIN =================

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {

        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("john@example.com");
        request.setPassword("123456");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123456", "encodedPassword"))
                .thenReturn(true);

        when(jwtUtil.generateToken(1L, "USER"))
                .thenReturn("jwt-token");

        LoginResponseDto response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("John", response.getUsername());
    }

    @Test
    void login_ShouldThrowException_WhenEmailNotFound() {

        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("abc@test.com");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login(request));

        assertEquals("Invalid Email or Password", exception.getMessage());
    }

    @Test
    void login_ShouldThrowException_WhenPasswordIsWrong() {

        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("john@example.com");
        request.setPassword("wrong");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong", "encodedPassword"))
                .thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login(request));

        assertEquals("Invalid Email or Password", exception.getMessage());
    }

    // ================= GET USER =================

    @Test
    void getUserById_ShouldReturnProfile() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        ProfileDto dto = authService.getUserById(1L);

        assertNotNull(dto);
        assertEquals("John", dto.getUsername());
        assertEquals("john@example.com", dto.getEmail());
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.getUserById(1L));

        assertEquals("User Not Found", exception.getMessage());
    }
}
