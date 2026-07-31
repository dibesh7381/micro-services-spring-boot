package AuthService.com.example.AuthService.service;

import AuthService.com.example.AuthService.dto.*;
import AuthService.com.example.AuthService.entity.User;
import AuthService.com.example.AuthService.event.UserCreatedEvent;
import AuthService.com.example.AuthService.publisher.KafkaPublisher;
import AuthService.com.example.AuthService.repository.UserRepository;
import AuthService.com.example.AuthService.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    // 🚀 CI/CD Trigger Test - AuthService K8s Rollout Magic

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final KafkaPublisher kafkaPublisher;

    // ===================== SIGNUP =====================

    public SignupResponseDto signup(SignupRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        UserCreatedEvent event = new UserCreatedEvent();
        event.setId(savedUser.getId());
        event.setUsername(savedUser.getUsername());
        event.setEmail(savedUser.getEmail());
        event.setRole(savedUser.getRole());
        event.setTotalUsers(userRepository.count());

        kafkaPublisher.publishUserCreated(event);

        SignupResponseDto response = new SignupResponseDto();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        response.setCreatedAt(savedUser.getCreatedAt());

        return response;
    }

    // ===================== LOGIN =====================
    public LoginResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email or Password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Email or Password");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getRole());

        LoginResponseDto response = new LoginResponseDto();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setToken(token);

        return response;
    }

    // ===================== INTERNAL API =====================

    public ProfileDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        ProfileDto dto = new ProfileDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;
    }
}