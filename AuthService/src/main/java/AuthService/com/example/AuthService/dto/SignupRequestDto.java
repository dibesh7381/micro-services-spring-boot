package AuthService.com.example.AuthService.dto;

import lombok.Data;

@Data
public class SignupRequestDto {

    private String username;
    private String email;
    private String password;

}