package AuthService.com.example.AuthService.event;

import lombok.Data;

@Data
public class UserCreatedEvent {

    private Long id;
    private String username;
    private String email;
    private String role;
    private Long totalUsers;
}