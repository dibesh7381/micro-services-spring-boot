package ProfileService.com.example.ProfileService.dto;

import lombok.Data;

@Data
public class ProfileDto {

    private Long id;
    private String username;
    private String email;
    private String role;

}