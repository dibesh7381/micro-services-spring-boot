package AuthService.com.example.AuthService.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginResponseDto {

    private Long id;
    private String username;
    private String email;
    private String role;
    private LocalDate createdAt;
    private String token;

}