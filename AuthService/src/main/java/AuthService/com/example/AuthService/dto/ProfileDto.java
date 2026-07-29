package AuthService.com.example.AuthService.dto;

import lombok.Data;

@Data
public class ProfileDto {

    private Long id;
    private String username;
    private String email;
    private String role;
}