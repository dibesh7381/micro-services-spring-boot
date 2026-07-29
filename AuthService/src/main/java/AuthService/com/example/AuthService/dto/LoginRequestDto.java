package AuthService.com.example.AuthService.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String email;
    private String password;

}