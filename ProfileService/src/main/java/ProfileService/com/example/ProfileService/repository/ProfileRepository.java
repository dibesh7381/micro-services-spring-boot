package ProfileService.com.example.ProfileService.repository;

import ProfileService.com.example.ProfileService.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}