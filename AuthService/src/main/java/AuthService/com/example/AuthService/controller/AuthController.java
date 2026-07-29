package AuthService.com.example.AuthService.controller;

import AuthService.com.example.AuthService.dto.*;
import AuthService.com.example.AuthService.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponseDto<SignupResponseDto>> signup(
            @RequestBody SignupRequestDto request) {

        SignupResponseDto data = authService.signup(request);

        ApiResponseDto<SignupResponseDto> response = new ApiResponseDto<>();
        response.setSuccess(true);
        response.setMessage("User Registered Successfully");
        response.setData(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(
            @RequestBody LoginRequestDto request) {

        LoginResponseDto data = authService.login(request);

        ApiResponseDto<LoginResponseDto> response = new ApiResponseDto<>();
        response.setSuccess(true);
        response.setMessage("Login Successful");
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/instance")
    @PreAuthorize("permitAll()")
    public String instance(HttpServletRequest request) {

        String hostname = System.getenv("HOSTNAME");

        log.info("Request {} handled by {}", request.getRequestURI(), hostname);

        return hostname;
    }

}