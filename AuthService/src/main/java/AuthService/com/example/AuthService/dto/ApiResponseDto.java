package AuthService.com.example.AuthService.dto;

import lombok.Data;

@Data
public class ApiResponseDto<T> {

    private boolean success;
    private String message;
    private T data;

}
