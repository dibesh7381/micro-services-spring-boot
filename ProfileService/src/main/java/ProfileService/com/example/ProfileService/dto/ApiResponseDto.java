package ProfileService.com.example.ProfileService.dto;
import lombok.Data;

@Data
public class ApiResponseDto<T> {

    private boolean success;
    private String message;
    private T data;

}
