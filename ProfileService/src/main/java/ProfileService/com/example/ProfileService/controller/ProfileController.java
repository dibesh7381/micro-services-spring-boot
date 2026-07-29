package ProfileService.com.example.ProfileService.controller;

import ProfileService.com.example.ProfileService.dto.ApiResponseDto;
import ProfileService.com.example.ProfileService.dto.ProfileDto;
import ProfileService.com.example.ProfileService.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/my-profile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponseDto<ProfileDto>> getMyProfile(Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        ProfileDto data = profileService.getMyProfile(userId);

        ApiResponseDto<ProfileDto> response = new ApiResponseDto<>();
        response.setSuccess(true);
        response.setMessage("Profile fetched successfully.");
        response.setData(data);

        return ResponseEntity.ok(response);
    }
}